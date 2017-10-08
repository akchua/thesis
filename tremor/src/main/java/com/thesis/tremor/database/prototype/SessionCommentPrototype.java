package com.thesis.tremor.database.prototype;

import java.util.List;

import com.thesis.tremor.database.entity.SessionComment;

public interface SessionCommentPrototype extends Prototype<SessionComment, Long> {

	List<SessionComment> findAllBySession(Long sessionId);
}
