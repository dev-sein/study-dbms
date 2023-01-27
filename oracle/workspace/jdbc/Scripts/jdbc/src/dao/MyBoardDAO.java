package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.BoardVO;

public class MyBoardDAO {
	public Connection connection;
	public PreparedStatement preparedStatement;
	public ResultSet resultSet;

	// 게시글 추가
	public void insert(BoardVO boardVO) {
		String query = "INSERT INTO TBL_BOARD"
				+ "BOARD_ID, BOARD_TITLE, BOARD_CONTENT, BOARD_REGISTERDATE, BOARD_UPDATE_DATE, USER_ID"
				+ "VALUES(SEQ.BOARD.NEXTVAL, ?, ?, SYSDATE, SYSDATE, ?)";
		connection = DBConnecter.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, boardVO.getBoardTitle());
			preparedStatement.setString(2, boardVO.getBoardContent());
			preparedStatement.setLong(3, boardVO.getUserId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("INSERT SQL문");
			e.printStackTrace();
		}
		catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if(resultSet != null) {
	               resultSet.close();
	            }
	            if(preparedStatement != null) {
	               preparedStatement.close();
	            }
	            if(connection != null) {
	               connection.close();
	            }
	         } catch (SQLException e) {
	            throw new RuntimeException(e);
	         }
	      }
	}

	// 게시글 조회
	
	
	
	// 게시글 수정
	// 게시글 삭제
	// 게시글 전체 조회

}
