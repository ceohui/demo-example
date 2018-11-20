package cn.zzh.demo.drools.redenvelope;

/**
 * 红包模型
 */
public class RedEnvelopeModel {

	private long userId;

	private String deviceId;

	private FigureDevice figureDevice;

	private FigureUser figureUser;

	private FigureTrade figureTrade;

	private FigureContent figureContent;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public FigureDevice getFigureDevice() {
		return figureDevice;
	}

	public void setFigureDevice(FigureDevice figureDevice) {
		this.figureDevice = figureDevice;
	}

	public FigureUser getFigureUser() {
		return figureUser;
	}

	public void setFigureUser(FigureUser figureUser) {
		this.figureUser = figureUser;
	}

	public FigureTrade getFigureTrade() {
		return figureTrade;
	}

	public void setFigureTrade(FigureTrade figureTrade) {
		this.figureTrade = figureTrade;
	}

	public FigureContent getFigureContent() {
		return figureContent;
	}

	public void setFigureContent(FigureContent figureContent) {
		this.figureContent = figureContent;
	}
}
