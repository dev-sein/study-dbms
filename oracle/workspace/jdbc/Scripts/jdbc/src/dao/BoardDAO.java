package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.BoardDTO;
import domain.BoardVO;

public class BoardDAO {
   public Connection connection; //연결 객체
   public PreparedStatement preparedStatement; //쿼리 관리 객체
   public ResultSet resultSet; //결과 테이블 객체
   
//   게시글 추가
   public void insert(BoardVO boardVO) {
      String query = "INSERT INTO TBL_BOARD"
            + "(BOARD_ID, BOARD_TITLE, BOARD_CONTENT, BOARD_REGISTER_DATE, BOARD_UPDATE_DATE, USER_ID) "
            + "VALUES(SEQ_BOARD.NEXTVAL, ?, ?, SYSDATE, SYSDATE, ?)";
      connection = DBConnecter.getConnection();
      try {
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, boardVO.getBoardTitle());
         preparedStatement.setString(2, boardVO.getBoardContent());
         preparedStatement.setLong(3, boardVO.getUserId());
         preparedStatement.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
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
//   게시글 조회
   public BoardDTO select(Long boardId) {
      String query = "SELECT BOARD_ID, BOARD_TITLE, BOARD_CONTENT, BOARD_REGISTER_DATE, " 
            + " BOARD_UPDATE_DATE, U.USER_ID, USER_IDENTIFICATION, USER_NAME, USER_PASSWORD, "
            + " USER_PHONE, USER_NICKNAME, USER_EMAIL, USER_ADDRESS, USER_BIRTH, USER_GENDER, "
            + " USER_RECOMMENDER_ID "
            + "FROM TBL_USER U JOIN TBL_BOARD B " //join user 테이블과 board 테이블
            + "ON U.USER_ID = B.USER_ID AND BOARD_ID = ?"; //게시글에 작성자의 정보까지 보여야하기 때문에 
      
      BoardDTO boardDTO = null; //boardDTO 클래스 초기화
      int index = 0; //숫자 index 
      connection = DBConnecter.getConnection(); //연결
      try {
         preparedStatement = connection.prepareStatement(query); //쿼리ㄱ
         preparedStatement.setLong(1, boardId); //boardId 넣기
         resultSet = preparedStatement.executeQuery(); //resultSet에 결과값 넣기
         if(resultSet.next()) { //resultSet이 true 라면
            boardDTO = new BoardDTO(); //boardDTO 생성자 , 두가지를 한꺼번에 담을 수 있는 더 큰 바구니가 필요하기 때문에 boardDTO를 사용 
            boardDTO.setBoardId(resultSet.getLong(++index)); //넣으세요.. 쭈욱...
            boardDTO.setBoardTitle(resultSet.getString(++index));
            boardDTO.setBoardContent(resultSet.getString(++index));
            boardDTO.setBoardRegisterDate(resultSet.getString(++index));
            boardDTO.setBoardUpdateDate(resultSet.getString(++index));
            boardDTO.setUserId(resultSet.getLong(++index));
            boardDTO.setUserIdentification(resultSet.getString(++index));
            boardDTO.setUserName(resultSet.getString(++index));
            boardDTO.setUserPassword(resultSet.getString(++index));
            boardDTO.setUserPhone(resultSet.getString(++index));
            boardDTO.setUserNickname(resultSet.getString(++index));
            boardDTO.setUserEmail(resultSet.getString(++index));
            boardDTO.setUserAddress(resultSet.getString(++index));
            boardDTO.setUserBirth(resultSet.getString(++index));
            boardDTO.setUserGender(resultSet.getString(++index));
            boardDTO.setUserRecommenderId(resultSet.getString(++index));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (Exception e) {
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
      
      return boardDTO;
   }
//   게시글 수정
   public void update(BoardVO boardVO) { //게시글 수정 메소드 
      String query = "UPDATE TBL_BOARD SET BOARD_TITLE = ?, BOARD_CONTENT = ?, BOARD_UPDATE_DATE = SYSDATE " 
            + "WHERE BOARD_ID = ?"; //게시글 수정 및 조회에는 board id가 들어감. 
      connection = DBConnecter.getConnection();
      try {
         preparedStatement = connection.prepareStatement(query); //쿼리에 넣기
         preparedStatement.setString(1, boardVO.getBoardTitle()); //boardVO의 게시글 타이틀을 set함
         preparedStatement.setString(2, boardVO.getBoardContent()); //boradVO의 게시글 내용을 set함 
         preparedStatement.setLong(3, boardVO.getBoardId()); //boardID를 세팅함
         preparedStatement.executeUpdate(); //내용 반영
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
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
   
//   게시글 삭제
   public void delete(Long boardId) { //게시글 삭제, 매개변수: boardID
      String query = "DELETE FROM TBL_BOARD WHERE BOARD_ID = ?"; //board_ID 받아서 delete 함 
      
      connection = DBConnecter.getConnection(); //db연결
      try {
         preparedStatement = connection.prepareStatement(query); //쿼리 넣기
         preparedStatement.setLong(1, boardId); //지울 게시글 번호
         preparedStatement.executeUpdate(); //delete하고 query update 
         
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
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
   
//   게시글 전체 조회
   public ArrayList<BoardDTO> selectAll(){ //전체 게시글 조회, ArrayList로 반환, 
      String query = "SELECT BOARD_ID, BOARD_TITLE, BOARD_CONTENT, BOARD_REGISTER_DATE, "
            + " BOARD_UPDATE_DATE, U.USER_ID, USER_IDENTIFICATION, USER_NAME, USER_PASSWORD, "
            + " USER_PHONE, USER_NICKNAME, USER_EMAIL, USER_ADDRESS, USER_BIRTH, USER_GENDER, "
            + " USER_RECOMMENDER_ID "
            + "FROM TBL_USER U JOIN TBL_BOARD B " //USER, BOARD 테이블 조인
            + "ON U.USER_ID = B.USER_ID"; //유저아이디로 조인함 
      
      
      BoardDTO boardDTO = null; //DTO 초기화 
      ArrayList<BoardDTO> boards = new ArrayList<BoardDTO>(); //boards 생성자 만들기 
      int index = 0; //숫자 쓰기
      connection = DBConnecter.getConnection(); //DBCON 연결 
      try {
         preparedStatement = connection.prepareStatement(query); //쿼리에 넣기
         resultSet = preparedStatement.executeQuery(); //
         while(resultSet.next()) { //몇개일지 모르니까 while문 반복 돌리기 
            index = 0;
            boardDTO = new BoardDTO(); //while문에 쓰기 위한 boardDTO 생성자 
            boardDTO.setBoardId(resultSet.getLong(++index)); //resultSet에 전부 다 출력하기 
            boardDTO.setBoardTitle(resultSet.getString(++index));
            boardDTO.setBoardContent(resultSet.getString(++index));
            boardDTO.setBoardRegisterDate(resultSet.getString(++index));
            boardDTO.setBoardUpdateDate(resultSet.getString(++index));
            boardDTO.setUserId(resultSet.getLong(++index));
            boardDTO.setUserIdentification(resultSet.getString(++index));
            boardDTO.setUserName(resultSet.getString(++index));
            boardDTO.setUserPassword(resultSet.getString(++index));
            boardDTO.setUserPhone(resultSet.getString(++index));
            boardDTO.setUserNickname(resultSet.getString(++index));
            boardDTO.setUserEmail(resultSet.getString(++index));
            boardDTO.setUserAddress(resultSet.getString(++index));
            boardDTO.setUserBirth(resultSet.getString(++index));
            boardDTO.setUserGender(resultSet.getString(++index));
            boardDTO.setUserRecommenderId(resultSet.getString(++index));
            boards.add(boardDTO);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (Exception e) {
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
      
      return boards;
   }
}



















