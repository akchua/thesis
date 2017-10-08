package com.thesis.tremor.database.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.thesis.tremor.database.entity.base.BaseObject;

@Entity(name = "SessionComment")
@Table(name = SessionComment.TABLE_NAME)
public class SessionComment extends BaseObject {

	private static final long serialVersionUID = 8770632489301689202L;
	
	public static final String TABLE_NAME = "session_comment";
	
	private Session session;
	
	private User commenter;
	
	private String comment;

	@ManyToOne(targetEntity = Session.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id")
	@Where(clause = "valid = 1")
	@NotFound(action = NotFoundAction.IGNORE)
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "commenter_id")
	@Where(clause = "valid = 1")
	@NotFound(action = NotFoundAction.IGNORE)
	public User getCommenter() {
		return commenter;
	}

	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}

	@Basic
	@Column(name = "comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
