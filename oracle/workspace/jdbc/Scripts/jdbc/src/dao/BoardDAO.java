package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.BoardDTO;
import domain.BoardVO;

public class BoardDAO {
   public Connection connection; //���� ��ü
   public PreparedStatement preparedStatement; //���� ���� ��ü
   public ResultSet resultSet; //��� ���̺� ��ü
   
//   �Խñ� �߰�
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
//   �Խñ� ��ȸ
   public BoardDTO select(Long boardId) {
      String query = "SELECT BOARD_ID, BOARD_TITLE, BOARD_CONTENT, BOARD_REGISTER_DATE, " 
            + " BOARD_UPDATE_DATE, U.USER_ID, USER_IDENTIFICATION, USER_NAME, USER_PASSWORD, "
            + " USER_PHONE, USER_NICKNAME, USER_EMAIL, USER_ADDRESS, USER_BIRTH, USER_GENDER, "
            + " USER_RECOMMENDER_ID "
            + "FROM TBL_USER U JOIN TBL_BOARD B " //join user ���̺�� board ���̺�
            + "ON U.USER_ID = B.USER_ID AND BOARD_ID = ?"; //�Խñۿ� �ۼ����� �������� �������ϱ� ������ 
      
      BoardDTO boardDTO = null; //boardDTO Ŭ���� �ʱ�ȭ
      int index = 0; //���� index 
      connection = DBConnecter.getConnection(); //����
      try {
         preparedStatement = connection.prepareStatement(query); //������
         preparedStatement.setLong(1, boardId); //boardId �ֱ�
         resultSet = preparedStatement.executeQuery(); //resultSet�� ����� �ֱ�
         if(resultSet.next()) { //resultSet�� true ���
            boardDTO = new BoardDTO(); //boardDTO ������ , �ΰ����� �Ѳ����� ���� �� �ִ� �� ū �ٱ��ϰ� �ʿ��ϱ� ������ boardDTO�� ��� 
            boardDTO.setBoardId(resultSet.getLong(++index)); //��������.. �޿�...
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
//   �Խñ� ����
   public void update(BoardVO boardVO) { //�Խñ� ���� �޼ҵ� 
      String query = "UPDATE TBL_BOARD SET BOARD_TITLE = ?, BOARD_CONTENT = ?, BOARD_UPDATE_DATE = SYSDATE " 
            + "WHERE BOARD_ID = ?"; //�Խñ� ���� �� ��ȸ���� board id�� ��. 
      connection = DBConnecter.getConnection();
      try {
         preparedStatement = connection.prepareStatement(query); //������ �ֱ�
         preparedStatement.setString(1, boardVO.getBoardTitle()); //boardVO�� �Խñ� Ÿ��Ʋ�� set��
         preparedStatement.setString(2, boardVO.getBoardContent()); //boradVO�� �Խñ� ������ set�� 
         preparedStatement.setLong(3, boardVO.getBoardId()); //boardID�� ������
         preparedStatement.executeUpdate(); //���� �ݿ�
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
   
//   �Խñ� ����
   public void delete(Long boardId) { //�Խñ� ����, �Ű�����: boardID
      String query = "DELETE FROM TBL_BOARD WHERE BOARD_ID = ?"; //board_ID �޾Ƽ� delete �� 
      
      connection = DBConnecter.getConnection(); //db����
      try {
         preparedStatement = connection.prepareStatement(query); //���� �ֱ�
         preparedStatement.setLong(1, boardId); //���� �Խñ� ��ȣ
         preparedStatement.executeUpdate(); //delete�ϰ� query update 
         
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
   
//   �Խñ� ��ü ��ȸ
   public ArrayList<BoardDTO> selectAll(){ //��ü �Խñ� ��ȸ, ArrayList�� ��ȯ, 
      String query = "SELECT BOARD_ID, BOARD_TITLE, BOARD_CONTENT, BOARD_REGISTER_DATE, "
            + " BOARD_UPDATE_DATE, U.USER_ID, USER_IDENTIFICATION, USER_NAME, USER_PASSWORD, "
            + " USER_PHONE, USER_NICKNAME, USER_EMAIL, USER_ADDRESS, USER_BIRTH, USER_GENDER, "
            + " USER_RECOMMENDER_ID "
            + "FROM TBL_USER U JOIN TBL_BOARD B " //USER, BOARD ���̺� ����
            + "ON U.USER_ID = B.USER_ID"; //�������̵�� ������ 
      
      
      BoardDTO boardDTO = null; //DTO �ʱ�ȭ 
      ArrayList<BoardDTO> boards = new ArrayList<BoardDTO>(); //boards ������ ����� 
      int index = 0; //���� ����
      connection = DBConnecter.getConnection(); //DBCON ���� 
      try {
         preparedStatement = connection.prepareStatement(query); //������ �ֱ�
         resultSet = preparedStatement.executeQuery(); //
         while(resultSet.next()) { //����� �𸣴ϱ� while�� �ݺ� ������ 
            index = 0;
            boardDTO = new BoardDTO(); //while���� ���� ���� boardDTO ������ 
            boardDTO.setBoardId(resultSet.getLong(++index)); //resultSet�� ���� �� ����ϱ� 
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



















