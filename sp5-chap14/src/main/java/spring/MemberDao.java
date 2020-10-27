package spring;
import java.util.Collection;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class MemberDao {
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Member> memRowMapper = 
		new RowMapper<Member>() {
		@Override
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException{
			Member member = new Member(
					rs.getString("EMAIL"),
					rs.getString("PASSWORD"),
					rs.getString("NAME"),
					rs.getTimestamp("REGDATE").toLocalDateTime());
			member.setId(rs.getLong("ID"));
			return member;
		}
	};

	
	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Member selectByEmail(String email) {
		List<Member> result = jdbcTemplate.query(
				"select * from MEMBER where EMAIL =?",
				 memRowMapper,email);
		return result.isEmpty()?null:result.get(0);
	}
	
	public void insert(final Member member) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
				PreparedStatement pstmt = con.prepareStatement(
						"insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) values(?,?,?,?)",
						new String[]{"ID"});
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());
				pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegiserDateTime())); 
				return pstmt;
			}
		},keyHolder);
		
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());
	}
	
	public void update(Member member) {
		jdbcTemplate.update(
				"update MEMBER set NAME =?, PASSWORD= ? where EMAIL =?",
				member.getName(), member.getPassword(), member.getEmail());
	}
	
	public List<Member> selectAll(){
		List<Member> result = jdbcTemplate.query(
				"select * from MEMBER", memRowMapper);
		return result;
	}
	
	public int count() {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from MEMBER",Integer.class);
		return count;
				
	}
	
	public List<Member> selectByRegdate(LocalDateTime from, LocalDateTime to){
		List<Member> results = jdbcTemplate.query(
			"select * from MEMBER where REGDATE between ? and ? order by REGDATE desc",
			 memRowMapper,from, to);
		return results;
	}
	
	public Member selectById(Long memId) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where ID =?",memRowMapper, memId);
		return results.isEmpty() ? null : results.get(0);
	}
}
