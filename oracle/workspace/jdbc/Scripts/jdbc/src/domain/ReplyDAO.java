package domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DBConnecter;
import dao.UserDAO;

import domain.ReplyDTO;
import domain.ReplyVO;


public class ReplyDAO {
	public Connection connection; //DB ���� ��ü
	public PreparedStatement preparedStatement; //���� ���� ��ü
	public ResultSet resultSet; //��� ���̺� ��ü
		
	//���� �߰�
	public void insert(ReplyVO replyVO, Long target) { //������ ���, ����� ��ȣ(ReplyId)�� �𸣱� ������ target�� �޾ƿ;� ��. TARGET=GROUP ID 
	      String query = "INSERT INTO TBL_REPLY" //REPLY ���̺� ������ 
	              + "(REPLY_ID, REPLY_CONTENT, USER_ID, BOARD_ID, REPLY_GROUP, REPLY_DEPTH) " //��۹�ȣ, ��۳���, ������ȣ, �Խ��ǹ�ȣ, ��� �׷�, ����� ����  
	              + "VALUES(SEQ_REPLY.NEXTVAL, ?, ?, ?, ?, (SELECT REPLY_DEPTH + 1 FROM TBL_REPLY WHERE REPLY_ID = ?))"; //DEPTB, �� Ÿ���� ���� �� +1�� �ؾ���. ������ ��� Ÿ�� ��ȣ�� �ҷ���. �� ��ȣ���� +1
	connection = DBConnecter.getConnection(); //DB ���� 
	try {
		preparedStatement = connection.prepareStatement(query); //���� ���� 
		preparedStatement.setString(1, replyVO.getReplyContent()); //��� ���� ���� 
		preparedStatement.setLong(2, UserDAO.userId); //�α����ϰ� �� �Ŵϱ� ���ǿ� �ִ� userid�� �����;� �� 
		preparedStatement.setLong(3, replyVO.getBoardId()); //�Խ��� ��ȣ 
		preparedStatement.setLong(4, target); //�����
		preparedStatement.setLong(5, target); //������� ��� 
		
		preparedStatement.executeUpdate(); //��� �߰� �Ϸ�
		
	} catch (SQLException e) { //���� �߻� 
		System.out.println("INSERT SQL�� ����");
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
	
	//��� �߰�, �����ε�, ������� �������� Ȯ���ؾ��� 	
	   public void insert(ReplyVO replyVO) { //�Ϲ� ��� �߰�, replyVO��ü�� �޾ƿ�
		      String query = "INSERT INTO TBL_REPLY" //reply ���̺� ��� �߰� ����
		            + "(REPLY_ID, REPLY_CONTENT, USER_ID, BOARD_ID, REPLY_GROUP, REPLY_DEPTH) "
		            + "VALUES(SEQ_REPLY.NEXTVAL, ?, ?, ?, SEQ_REPLY.CURRVAL, 0)"; //depth: �Ϲ� ����̹Ƿ� depth�� �׻� 0��. group: ���� seq�� �޾ƿ�  
		connection = DBConnecter.getConnection(); //DB���� 
		try {
			preparedStatement = connection.prepareStatement(query); //���� ���� 
			preparedStatement.setString(1, replyVO.replyContent); //��� ���� 
			preparedStatement.setLong(2, UserDAO.userId); //�α����ϰ� �� �Ŵϱ� ���ǿ� �ִ� userid�� �����;� �� 
			preparedStatement.setLong(3, replyVO.getBoardId()); //�Խ��� ���̵� 
			preparedStatement.executeUpdate(); //�Ϲ� ��� �߰� �Ϸ�
			
		} catch (SQLException e) { //����
			System.out.println("INSERT SQL�� ����");
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
	            throw new RuntimeException(e); //e�� ����ϴ� ���� pre~ conn~���� �߻��� ������ e�� ��Ƽ� �����ϱ� ������ 
	         }
	      }
	   }
	
	//��� ��ü ��ȸ
	   public ArrayList<ReplyDTO> selectAll(){ //��ȯ ���� �������̹Ƿ� ArrayList, �� ū �ٱ��� DTO�� ����� ��� 
		      String query = "SELECT NVL(REPLY_COUNT, 0) REPLY_COUNT, REPLY_ID, REPLY_CONTENT, R2.USER_ID, BOARD_ID, REPLY_REGISTER_DATE, REPLY_UPDATE_DATE, " 
		            + "R2.REPLY_GROUP, REPLY_DEPTH, " 
		            + "USER_IDENTIFICATION, USER_NAME, USER_PASSWORD, " 
		            + "USER_PHONE, USER_NICKNAME, USER_EMAIL, USER_ADDRESS, USER_BIRTH, " 
		            + "USER_GENDER, USER_RECOMMENDER_ID " 
		            + "FROM (SELECT REPLY_GROUP, COUNT(REPLY_ID) - 1 REPLY_COUNT FROM TBL_REPLY GROUP BY REPLY_GROUP) R1 RIGHT OUTER JOIN VIEW_REPLY_USER R2 "
		            + "ON R1.REPLY_GROUP = R2.REPLY_GROUP AND R1.REPLY_GROUP = R2.REPLY_ID";
		      
		      //REPLY_COUNT: (LIKE ��Ʃ��) ������ �ִ� ��� ����� ��� ���� ��ȯ.
		      //VIEW�� ����� JOIN��
		      //SUBQUERY R1:  REPLY ���̺��� GROUPBY�� ���ö��� �׷��� ���� ������ ��(��� ���� Ȯ�ο� ���̺�)
		     //VIEW(R2) : reply�� user ������, on userId
		     //JOIN(R2�� R1): �˾Ƴ���: USER ���̵�� ���ö��� �׷� ������ȣ�� ����/ USERID�� ���� ���̺�() ���� ����(���)�� ��. -> USERID�� ���� ��µǾ�� �ϹǷ� RIGHT OUTER JOIN 
		    
		      //"ON R1.REPLY_GROUP = R2.REPLY_GROUP AND R1.REPLY_GROUP = R2.REPLY_ID"; 
		      // R1.REPLY_GROUP : REPLY ���̺��� REPLY_�׷����� GROUP BY�� ��, ������ ī��Ʈ�� // R2.REPLY_GROUP: ����� �׷� ������  // ����
		      // R1.REPLY_GROUP : REPLY ���̺��� REPLY_�׷����� GROUP BY�� ��, ������ GROUP_ID �� �� // R2.REPLY_ID :: USER ���̺��� USERID�� REPLY ���̺��� USERID�� ���� ��������� REPLY_ID ��� ��ȣ�� ���ƾ� ��. 
		      ReplyDTO replyDTO = null; //REPLYDTO�� ����ϱ� ���� ����
		      int index = 0; //INDEX ��� ���� ����
		      ArrayList<ReplyDTO> replies = new ArrayList<ReplyDTO>(); //ARLT ������
		      
		      connection = DBConnecter.getConnection(); //DB ���� 
		      try {
		         preparedStatement = connection.prepareStatement(query); //���� ����
		         resultSet = preparedStatement.executeQuery(); //resultSet�� ����� ���� 
		         
		         while(resultSet.next()) { //resultSet ���� true ��� 
		            index = 0; //�ε��� �����
		            replyDTO = new ReplyDTO(); //replyDTO�� ReplyDTO ����
		            replyDTO.setReplyCount(resultSet.getLong(++index)); //�� ����....
		            replyDTO.setReplyId(resultSet.getLong(++index));
		            replyDTO.setReplyContent(resultSet.getString(++index));
		            replyDTO.setUserId(resultSet.getLong(++index));
		            replyDTO.setBoardId(resultSet.getLong(++index));
		            replyDTO.setReplyRegisterDate(resultSet.getString(++index));
		            replyDTO.setReplyUpdateDate(resultSet.getString(++index));
		            replyDTO.setReplyGroup(resultSet.getLong(++index));
		            replyDTO.setReplyDepth(resultSet.getLong(++index));
		            replyDTO.setUserIdentification(resultSet.getString(++index));
		            replyDTO.setUserName(resultSet.getString(++index));
		            replyDTO.setUserPassword(resultSet.getString(++index));
		            replyDTO.setUserPhone(resultSet.getString(++index));
		            replyDTO.setUserNickname(resultSet.getString(++index));
		            replyDTO.setUserEmail(resultSet.getString(++index));
		            replyDTO.setUserAddress(resultSet.getString(++index));
		            replyDTO.setUserBirth(resultSet.getString(++index));
		            replyDTO.setUserGender(resultSet.getString(++index));
		            replyDTO.setUserRecommenderId(resultSet.getString(++index));
		            replies.add(replyDTO);
		         }
		         
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
		      return replies;
		   }

	

	//���� ���� 
	   
	public void deleteReReply (Long replyId) { //��� ����, ���� �����̱� ������ ��ư�� �� ��. �׷��� �޼ҵ嵵 �� �� ������ ��.��
		String query = "DELETE FROM TBL_REPLY WHERE REPLY_ID = ?";
		connection = DBConnecter.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, replyId); //���� ��� ��ȣ
	        preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DELETE SQL�� ����");
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
	
	
	//��� ����, ���� �����̱� ������ ��ư�� �� ��. �׷��� �޼ҵ嵵 �� �� ������ ��
	//��� ���� 
	public void deleteReply(Long groupId) { 
		String query = "DELETE FROM TBL_REPLY WHERE REPLY_GROUP = ?";
		connection = DBConnecter.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, groupId); //���� ��� ��ȣ
	        preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DELETE SQL�� ����");
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
	
	
	
	//��� ����
	public void updateReply (ReplyVO replyVO) { //REPLYID�� VO�� �����ϱ� �� REPLYVO�� ����. ������ �� CONTENT �� 
		String query = "UPDATE TBL_REPLY SET REPLY_CONTENT = ? REPLY_UPDATE_DATE = SYSDATE, REPLY_REGISTER_DATE = SYSDATE WHERE REPLY_ID = ?";
		connection = DBConnecter.getConnection();
		try {
			preparedStatement= connection.prepareStatement(query);
			preparedStatement.setString(1, replyVO.getReplyContent());
			preparedStatement.setLong(2, replyVO.getReplyId());
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("UPDATE SQL�� ����");
			e.printStackTrace();
		}catch (Exception e) {
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
	
}
