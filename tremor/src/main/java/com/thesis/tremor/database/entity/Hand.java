package com.thesis.tremor.database.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.thesis.tremor.database.entity.base.BaseObject;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
@Entity(name = "Hand")
@Table(name = Hand.TABLE_NAME)
public class Hand extends BaseObject {

	private static final long serialVersionUID = 4744965978317121380L;

	public static final String TABLE_NAME = "hand";
}
