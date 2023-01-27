package domain;

public class ReplyDTO {
   private Long replyCount;
   private Long replyId;
   private String replyContent;
   private String replyRegisterDate;
   private String replyUpdateDate;
   private Long userId;
   private Long boardId;
   private Long replyGroup;
   private Long replyDepth;
   private String userIdentification;
   private String userName;
   private String userPassword;
   private String userPhone;
   private String userNickname;
   private String userEmail;
   private String userAddress;
   private String userBirth;
   private String userGender;
   private String userRecommenderId;
   
   public ReplyDTO() {;}

   public Long getReplyCount() {
      return replyCount;
   }

   public void setReplyCount(Long replyCount) {
      this.replyCount = replyCount;
   }



   public Long getReplyId() {
      return replyId;
   }

   public void setReplyId(Long replyId) {
      this.replyId = replyId;
   }

   public String getReplyContent() {
      return replyContent;
   }

   public void setReplyContent(String replyContent) {
      this.replyContent = replyContent;
   }

   public String getReplyRegisterDate() {
      return replyRegisterDate;
   }

   public void setReplyRegisterDate(String replyRegisterDate) {
      this.replyRegisterDate = replyRegisterDate;
   }

   public String getReplyUpdateDate() {
      return replyUpdateDate;
   }

   public void setReplyUpdateDate(String replyUpdateDate) {
      this.replyUpdateDate = replyUpdateDate;
   }

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public Long getBoardId() {
      return boardId;
   }

   public void setBoardId(Long boardId) {
      this.boardId = boardId;
   }

   public Long getReplyGroup() {
      return replyGroup;
   }

   public void setReplyGroup(Long replyGroup) {
      this.replyGroup = replyGroup;
   }

   public Long getReplyDepth() {
      return replyDepth;
   }

   public void setReplyDepth(Long replyDepth) {
      this.replyDepth = replyDepth;
   }

   public String getUserIdentification() {
      return userIdentification;
   }

   public void setUserIdentification(String userIdentification) {
      this.userIdentification = userIdentification;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getUserPassword() {
      return userPassword;
   }

   public void setUserPassword(String userPassword) {
      this.userPassword = userPassword;
   }

   public String getUserPhone() {
      return userPhone;
   }

   public void setUserPhone(String userPhone) {
      this.userPhone = userPhone;
   }

   public String getUserNickname() {
      return userNickname;
   }

   public void setUserNickname(String userNickname) {
      this.userNickname = userNickname;
   }

   public String getUserEmail() {
      return userEmail;
   }

   public void setUserEmail(String userEmail) {
      this.userEmail = userEmail;
   }

   public String getUserAddress() {
      return userAddress;
   }

   public void setUserAddress(String userAddress) {
      this.userAddress = userAddress;
   }

   public String getUserBirth() {
      return userBirth;
   }

   public void setUserBirth(String userBirth) {
      this.userBirth = userBirth;
   }

   public String getUserGender() {
      return userGender;
   }

   public void setUserGender(String userGender) {
      this.userGender = userGender;
   }

   public String getUserRecommenderId() {
      return userRecommenderId;
   }

   public void setUserRecommenderId(String userRecommenderId) {
      this.userRecommenderId = userRecommenderId;
   }

   @Override
   public String toString() {
      return "ReplyDTO [replyCount=" + replyCount + ", replyId=" + replyId + ", replyContent=" + replyContent
            + ", replyRegisterDate=" + replyRegisterDate + ", replyUpdateDate=" + replyUpdateDate + ", userId="
            + userId + ", boardId=" + boardId + ", replyGroup=" + replyGroup + ", replyDepth=" + replyDepth
            + ", userIdentification=" + userIdentification + ", userName=" + userName + ", userPassword="
            + userPassword + ", userPhone=" + userPhone + ", userNickname=" + userNickname + ", userEmail="
            + userEmail + ", userAddress=" + userAddress + ", userBirth=" + userBirth + ", userGender=" + userGender
            + ", userRecommenderId=" + userRecommenderId + "]";
   }
}




