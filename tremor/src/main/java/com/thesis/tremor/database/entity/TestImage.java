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

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
@Entity(name = "TestImage")
@Table(name = TestImage.TABLE_NAME)
public class TestImage extends BaseObject {

	private static final long serialVersionUID = 4989871877172660696L;

	public static final String TABLE_NAME = "test_image";
	
	private Test test;
	
	private String fileName;

	@ManyToOne(targetEntity = Test.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "test_id")
	@Where(clause = "valid = 1")
	@NotFound(action = NotFoundAction.IGNORE)
	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	@Basic
	@Column(name = "file_name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
