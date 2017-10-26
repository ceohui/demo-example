package cn.zzh.demo.thread.lock;

public class Key {

	static final String SEPARATOR = "_";

	private String keyStr;

	private long writeTime;

	public Key(String... params){
		StringBuilder sb = new StringBuilder();
		//sb.append(clazz.getSimpleName());
		for(String param : params){
			sb.append(SEPARATOR).append(param);
		}
		keyStr = sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof Key){
			Key input = (Key)obj;
			if(input.getKeyStr().equals(getKeyStr())){
				return true;
			}
		}
		return false;
	}

	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	public long getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(long writeTime) {
		this.writeTime = writeTime;
	}
}
