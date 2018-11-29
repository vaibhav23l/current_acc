package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service
public class SequenceGenerator {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void setNamedParameterJdbcTemplate(
		NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public int getNextSequence(String table_name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table_name", table_name);

		String sql = "SELECT sequence_num FROM SEQUENCE WHERE table_name=:table_name";

		Integer result = namedParameterJdbcTemplate.queryForObject(sql, params,
				new SequenceMapper());

		result = result + 1;
		params.clear();
		params.put("table_name", table_name);
		params.put("sequence_num", result);

		String sql1 = "UPDATE SEQUENCE set sequence_num = :sequence_num where  :table_name = table_name ";

		int res = namedParameterJdbcTemplate.update(sql1, params);
		return result;
	}

	private static final class SequenceMapper implements RowMapper<Integer> {

		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {

			if (rs == null) {
				return 1;
			}
			return rs.getInt("sequence_num");
		}
	}
}
