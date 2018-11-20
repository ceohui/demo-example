package cn.zzh.demo.kuaishou.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class KsHistWorks {

	private String userId;

	private String kwaiId;

	@JSONField(name = "photoId")
	private String worksId;

	private String caption;

	@JSONField(name = "viewCount")
	private int numView;

	@JSONField(name = "likeCount")
	private int numLike;

	@JSONField(name = "commentCount")
	private int numCommon;

	private Date createTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKwaiId() {
		return kwaiId;
	}

	public void setKwaiId(String kwaiId) {
		this.kwaiId = kwaiId;
	}

	public String getWorksId() {
		return worksId;
	}

	public void setWorksId(String worksId) {
		this.worksId = worksId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getNumView() {
		return numView;
	}

	public void setNumView(int numView) {
		this.numView = numView;
	}

	public int getNumLike() {
		return numLike;
	}

	public void setNumLike(int numLike) {
		this.numLike = numLike;
	}

	public int getNumCommon() {
		return numCommon;
	}

	public void setNumCommon(int numCommon) {
		this.numCommon = numCommon;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
