package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import domain.UserVO;

public class MyUserDAO {

	public static Long userId;
	public Connection connection; // ���� ��ü
	public PreparedStatement preparedStatement; // ���� ���� ��ü
	public ResultSet resultSet; // ��� ���̺� ��ü

	// ���̵� �ߺ��˻� (my)
	public boolean myCheckId(String userIdentification) {
		String query = "SELECT COUNT(USER_ID) FROM TBL_USER WHERE USER_IDENTIFICATION = ? ";
		boolean result = false;
		connection = DBConnecter.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userIdentification);
			resultSet = preparedStatement.executeQuery();

			resultSet.next();
			result = resultSet.getInt(1) == 0;

		} catch (SQLException e) {
			System.out.println("myCheckId(String) SQL�� ����");
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	// ���̵� �ߺ��˻�(teacher)
	public boolean checkId(String userIdentification) {
		String query = "SELECT COUNT(USER_ID) FROM TBL_USER WHERE USER_IDENTIFICATION = ?";
		boolean result = false;
		connection = DBConnecter.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userIdentification);
			resultSet = preparedStatement.executeQuery();

			resultSet.next();
			result = resultSet.getInt(1) == 0;

		} catch (SQLException e) {
			System.out.println("checkId(String) SQL�� ����");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	// ȸ������

	public void insert(UserVO userVO) {
		String queryForJoin = "INSER INTO TBL_USER"
				+ "(USER_ID, USER_IDENTIFICATION, USER_NAME, USER_PASSWORD, USER_PHONE, USER_EMAIL, USER_ADDRESS, USER_RECOMMENDER_ID) "
				+ "VALUES(SEQ_USER.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
		// ��õ���� ���� ���, TBL_REC
		String queryForUpdateRecommendCount = "UPDATE TBL_RECOMMEND SET RECOMMEND_COUNT = RECOMMEND_COUNT + 1 WHERE USER_ID = ?";
		String queryForGetUserId = "SELECT USER_ID FROM TBL_USER WHERE USER_IDENTIFICATION = ?";
		// idenfication���� id��ȣ�� �����;���. �α����� �� �� �ƴ϶� SESSION�� ���� ���� ����.

		connection = DBConnecter.getConnection();
		try {

			preparedStatement = connection.prepareStatement(queryForJoin);
			preparedStatement.setString(1, userVO.getUserIdentification());
			preparedStatement.setString(2, userVO.getUserName());
			preparedStatement.setString(3, userVO.getUserPassword());
			preparedStatement.setString(4, userVO.getUserPhone());
			preparedStatement.setString(5, userVO.getUserEmail());
			preparedStatement.setString(6, userVO.getUserAddress());
			preparedStatement.setString(7, userVO.getUserRecommenderId());
			preparedStatement.executeUpdate(); // ȸ������ �� ������Ʈ

			if (userVO.getUserRecommenderId() != null) {
				preparedStatement = connection.prepareStatement(queryForGetUserId);
				preparedStatement.setString(1, userVO.getUserRecommenderId());
				resultSet = preparedStatement.executeQuery();
			}

			if (resultSet.next()) { // �Է��� ��õ�� ���̵� ������ ���
				preparedStatement = connection.prepareStatement(queryForUpdateRecommendCount);
				preparedStatement.setString(1, userVO.getUserRecommenderId());
				resultSet = preparedStatement.executeQuery();
			}

		} catch (SQLException e) {
			System.out.println("insert(UserVO) SQL�� ����");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// �α���
	public boolean login(String userIdentification, String userPassword) {
		String query = "SELECT USER_ID FROM TBL_USER WHERE USER_IDENTIFICATION =? USER_PASSWORD =?";
		boolean login = false;
		connection = DBConnecter.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userIdentification);
			preparedStatement.setString(2, userPassword);
			preparedStatement.execute();

		} catch (SQLException e) {
			System.out.println("LOGIN SQL�� ����");
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return login;
	}

	// ȸ�� Ż��
	public void delete(String userIdentification, String userPassword) {
		String query = "DELETE FROM TBL_USER WHERE USER_ID =?";
		connection = DBConnecter.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, userId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DELETE SQL�� ����");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// ���̵� ã��

	// ��й�ȣ ����
	public void updateUserPassword(String userPassword) {
		String query = "UPDATE TBL_USER SET USER_PASSWORD =? WHERE USER_ID = ? ";
		connection = DBConnecter.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, new String(Base64.getEncoder().encode(userPassword.getBytes())));
			preparedStatement.setLong(2, userId);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("ȸ������ ���� SQL�� ����");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// ȸ������ ����
	public void updateUserInfo(String userIdentification, String userPassword) {
		String query = "UPDATE TBL_USER SET USER_NICKNAME, USER_BIRTH, USER_ADDRESS WHERE USER_IDENTIFICATION =? USER_PASSWORD =?";
		connection = DBConnecter.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
		} catch (SQLException e) {
			System.out.println("ȸ������ ���� SQL�� ����");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// ȸ������ ��ȸ
	public UserVO select(Long userId) {
		String query = "SELECT USER_ID, USER_IDENTIFICATION, USER_NAME, USER_PASSWORD, USER_PHONE, USER_NICKNAME, USER_EMAIL, USER_ADDRESS, USER_BIRTH, USER_GENDER, USER_RECOMMENDER_ID"
				+ "FROM TBL_USER WHERE USER_ID = ? UESR_PASSWORD = ?";
		connection = DBConnecter.getConnection();
		try {
			UserVO userVO = null;
			preparedStatement.setLong(1, userId);
			resultSet = preparedStatement.executeQuery();
			preparedStatement = connection.prepareStatement(query);

			if(resultSet.next()) {
				userVO = new UserVO();
				userVO.setUserId(resultSet.getLong(1));
				userVO.setUserIdentification(resultSet.getString(2));
				userVO.setUserName(resultSet.getString(3));
				userVO.setUserPassword(resultSet.getString(4));
				userVO.setUserPhone(resultSet.getString(5));
				userVO.setUserNickname(resultSet.getString(6));
				userVO.setUserEmail(resultSet.getString(7));
				userVO.setUserAddress(resultSet.getString(8));
				userVO.setUserBirth(resultSet.getString(9));
				userVO.setUserGender(resultSet.getString(10));
				userVO.setUserRecommenderId(resultSet.getString(11));
			}
			
		} catch (SQLException e) {
			System.out.println("SELECT SQL�� ����");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return userVO;
	}
	// ��õ��
	// ���� ��õ�� ���
	// ���� ��õ�� ���
}
