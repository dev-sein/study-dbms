package domain;


public class ReplyVO {
	
	public Long replyId;
	public String replyContent;
	public String replyRegisterDate;
	public String replyUpdateDate;
	public Long userId;
	public Long boardId;
	public Long replyGroup;
	public Long replyDepth;

	public ReplyVO() {;}

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

	@Override
	public String toString() {
		return "ReplyVO [replyId=" + replyId + ", replyContent=" + replyContent + ", replyRegisterDate="
				+ replyRegisterDate + ", replyUpdateDate=" + replyUpdateDate + ", userId=" + userId + ", boardId="
				+ boardId + ", replyGroup=" + replyGroup + ", replyDepth=" + replyDepth + "]";
	}
	
	

}
