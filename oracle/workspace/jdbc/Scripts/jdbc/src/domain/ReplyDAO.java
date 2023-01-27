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
	public Connection connection; //DB 연결 객체
	public PreparedStatement preparedStatement; //쿼리 관리 객체
	public ResultSet resultSet; //결과 테이블 객체
		
	//대댓글 추가
	public void insert(ReplyVO replyVO, Long target) { //대댓글일 경우, 댓글의 번호(ReplyId)를 모르기 때문에 target을 받아와야 함. TARGET=GROUP ID 
	      String query = "INSERT INTO TBL_REPLY" //REPLY 테이블에 삽입함 
	              + "(REPLY_ID, REPLY_CONTENT, USER_ID, BOARD_ID, REPLY_GROUP, REPLY_DEPTH) " //댓글번호, 댓글내용, 유저번호, 게시판번호, 댓글 그룹, 댓글의 대댓글  
	              + "VALUES(SEQ_REPLY.NEXTVAL, ?, ?, ?, ?, (SELECT REPLY_DEPTH + 1 FROM TBL_REPLY WHERE REPLY_ID = ?))"; //DEPTB, 내 타겟의 정보 중 +1을 해야함. 대댓글인 경우 타겟 번호를 불러옴. 그 번호에서 +1
	connection = DBConnecter.getConnection(); //DB 연결 
	try {
		preparedStatement = connection.prepareStatement(query); //쿼리 연결 
		preparedStatement.setString(1, replyVO.getReplyContent()); //댓글 내용 삽입 
		preparedStatement.setLong(2, UserDAO.userId); //로그인하고 쓸 거니까 세션에 있는 userid를 가져와야 함 
		preparedStatement.setLong(3, replyVO.getBoardId()); //게시판 번호 
		preparedStatement.setLong(4, target); //원댓글
		preparedStatement.setLong(5, target); //원댓글의 답댓 
		
		preparedStatement.executeUpdate(); //댓글 추가 완료
		
	} catch (SQLException e) { //예외 발생 
		System.out.println("INSERT SQL문 오류");
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
	
	//댓글 추가, 오버로딩, 댓글인지 대댓글인지 확인해야함 	
	   public void insert(ReplyVO replyVO) { //일반 댓글 추가, replyVO객체를 받아옴
		      String query = "INSERT INTO TBL_REPLY" //reply 테이블에 댓글 추가 쿼리
		            + "(REPLY_ID, REPLY_CONTENT, USER_ID, BOARD_ID, REPLY_GROUP, REPLY_DEPTH) "
		            + "VALUES(SEQ_REPLY.NEXTVAL, ?, ?, ?, SEQ_REPLY.CURRVAL, 0)"; //depth: 일반 댓글이므로 depth는 항상 0임. group: 현재 seq를 받아옴  
		connection = DBConnecter.getConnection(); //DB연결 
		try {
			preparedStatement = connection.prepareStatement(query); //쿼리 연결 
			preparedStatement.setString(1, replyVO.replyContent); //댓글 내용 
			preparedStatement.setLong(2, UserDAO.userId); //로그인하고 쓸 거니까 세션에 있는 userid를 가져와야 함 
			preparedStatement.setLong(3, replyVO.getBoardId()); //게시판 아이디 
			preparedStatement.executeUpdate(); //일반 댓글 추가 완료
			
		} catch (SQLException e) { //예외
			System.out.println("INSERT SQL문 오류");
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
	            throw new RuntimeException(e); //e를 사용하는 이유 pre~ conn~에서 발생한 오류를 e가 담아서 전달하기 때문임 
	         }
	      }
	   }
	
	//댓글 전체 조회
	   public ArrayList<ReplyDTO> selectAll(){ //반환 행이 여러개이므로 ArrayList, 더 큰 바구니 DTO를 만들어 사용 
		      String query = "SELECT NVL(REPLY_COUNT, 0) REPLY_COUNT, REPLY_ID, REPLY_CONTENT, R2.USER_ID, BOARD_ID, REPLY_REGISTER_DATE, REPLY_UPDATE_DATE, " 
		            + "R2.REPLY_GROUP, REPLY_DEPTH, " 
		            + "USER_IDENTIFICATION, USER_NAME, USER_PASSWORD, " 
		            + "USER_PHONE, USER_NICKNAME, USER_EMAIL, USER_ADDRESS, USER_BIRTH, " 
		            + "USER_GENDER, USER_RECOMMENDER_ID " 
		            + "FROM (SELECT REPLY_GROUP, COUNT(REPLY_ID) - 1 REPLY_COUNT FROM TBL_REPLY GROUP BY REPLY_GROUP) R1 RIGHT OUTER JOIN VIEW_REPLY_USER R2 "
		            + "ON R1.REPLY_GROUP = R2.REPLY_GROUP AND R1.REPLY_GROUP = R2.REPLY_ID";
		      
		      //REPLY_COUNT: (LIKE 유튜브) 답댓글이 있는 경우 원댓과 답글 개수 반환.
		      //VIEW를 만들어 JOIN함
		      //SUBQUERY R1:  REPLY 테이블에서 GROUPBY로 리플라이 그룹을 묶어 개수를 셈(답글 개수 확인용 테이블)
		     //VIEW(R2) : reply와 user 조인함, on userId
		     //JOIN(R2와 R1): 알아낼것: USER 아이디와 리플라이 그룹 개수번호가 같음/ USERID가 같은 테이블() 에서 개수(답댓)를 셈. -> USERID는 전부 출력되어야 하므로 RIGHT OUTER JOIN 
		    
		      //"ON R1.REPLY_GROUP = R2.REPLY_GROUP AND R1.REPLY_GROUP = R2.REPLY_ID"; 
		      // R1.REPLY_GROUP : REPLY 테이블에서 REPLY_그룹으로 GROUP BY한 값, 원댓의 카운트가 // R2.REPLY_GROUP: 답글의 그룹 개수와  // 같고
		      // R1.REPLY_GROUP : REPLY 테이블에서 REPLY_그룹으로 GROUP BY한 값, 원댓의 GROUP_ID 값 과 // R2.REPLY_ID :: USER 테이블의 USERID와 REPLY 테이블의 USERID가 같은 결과값에서 REPLY_ID 댓글 번호가 같아야 함. 
		      ReplyDTO replyDTO = null; //REPLYDTO를 사용하기 위해 선언
		      int index = 0; //INDEX 사용 위해 선언
		      ArrayList<ReplyDTO> replies = new ArrayList<ReplyDTO>(); //ARLT 생성자
		      
		      connection = DBConnecter.getConnection(); //DB 연결 
		      try {
		         preparedStatement = connection.prepareStatement(query); //쿼리 연결
		         resultSet = preparedStatement.executeQuery(); //resultSet에 결과값 연결 
		         
		         while(resultSet.next()) { //resultSet 값이 true 라면 
		            index = 0; //인덱스 써야함
		            replyDTO = new ReplyDTO(); //replyDTO에 ReplyDTO 연결
		            replyDTO.setReplyCount(resultSet.getLong(++index)); //값 세팅....
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

	

	//대댓글 삭제 
	   
	public void deleteReReply (Long replyId) { //댓글 삭제, 대댓글 삭제이기 때문에 버튼이 두 개. 그래서 메소드도 두 개 만들어야 함.ㅇ
		String query = "DELETE FROM TBL_REPLY WHERE REPLY_ID = ?";
		connection = DBConnecter.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, replyId); //지울 댓글 번호
	        preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DELETE SQL문 오류");
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
	
	
	//댓글 삭제, 대댓글 삭제이기 때문에 버튼이 두 개. 그래서 메소드도 두 개 만들어야 함
	//댓글 삭제 
	public void deleteReply(Long groupId) { 
		String query = "DELETE FROM TBL_REPLY WHERE REPLY_GROUP = ?";
		connection = DBConnecter.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, groupId); //지울 댓글 번호
	        preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DELETE SQL문 오류");
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
	
	
	
	//댓글 수정
	public void updateReply (ReplyVO replyVO) { //REPLYID가 VO에 있으니까 걍 REPLYVO를 받음. 수정할 건 CONTENT 임 
		String query = "UPDATE TBL_REPLY SET REPLY_CONTENT = ? REPLY_UPDATE_DATE = SYSDATE, REPLY_REGISTER_DATE = SYSDATE WHERE REPLY_ID = ?";
		connection = DBConnecter.getConnection();
		try {
			preparedStatement= connection.prepareStatement(query);
			preparedStatement.setString(1, replyVO.getReplyContent());
			preparedStatement.setLong(2, replyVO.getReplyId());
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("UPDATE SQL문 오류");
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
