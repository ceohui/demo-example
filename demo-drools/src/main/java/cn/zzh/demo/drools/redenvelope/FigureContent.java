package cn.zzh.demo.drools.redenvelope;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容画像
 */
public class FigureContent {

	/**
	 * 录播收听时长（秒）
	 */
	private int recListenDuration;

	/**
	 * 录播收听数量
	 */
	private int recListenCount;

	/**
	 * 录播评论次数
	 */
	private int recCommentCount;

	/**
	 * 录播发布声音数量
	 */
	private int recPublishCount;

	/**
	 * 直播收听时长（秒）
	 */
	private int liveListenDuration;

	/**
	 * 直播收听数量
	 */
	private int liveListenCount;

	/**
	 * 直播评论次数
	 */
	private int liveCommentCount;

	/**
	 * 直播发布次数
	 */
	private int livePublishCount;

	/**
	 * 直播发布平均时长（秒）
	 */
	private int livePublishAvgDuration;


	public int getRecListenDuration() {
		return recListenDuration;
	}

	public void setRecListenDuration(int recListenDuration) {
		this.recListenDuration = recListenDuration;
	}

	public int getRecListenCount() {
		return recListenCount;
	}

	public void setRecListenCount(int recListenCount) {
		this.recListenCount = recListenCount;
	}

	public int getRecCommentCount() {
		return recCommentCount;
	}

	public void setRecCommentCount(int recCommentCount) {
		this.recCommentCount = recCommentCount;
	}

	public int getRecPublishCount() {
		return recPublishCount;
	}

	public void setRecPublishCount(int recPublishCount) {
		this.recPublishCount = recPublishCount;
	}

	public int getLiveListenDuration() {
		return liveListenDuration;
	}

	public void setLiveListenDuration(int liveListenDuration) {
		this.liveListenDuration = liveListenDuration;
	}

	public int getLiveListenCount() {
		return liveListenCount;
	}

	public void setLiveListenCount(int liveListenCount) {
		this.liveListenCount = liveListenCount;
	}

	public int getLiveCommentCount() {
		return liveCommentCount;
	}

	public void setLiveCommentCount(int liveCommentCount) {
		this.liveCommentCount = liveCommentCount;
	}

	public int getLivePublishCount() {
		return livePublishCount;
	}

	public void setLivePublishCount(int livePublishCount) {
		this.livePublishCount = livePublishCount;
	}

	public int getLivePublishAvgDuration() {
		return livePublishAvgDuration;
	}

	public void setLivePublishAvgDuration(int livePublishAvgDuration) {
		this.livePublishAvgDuration = livePublishAvgDuration;
	}
}
