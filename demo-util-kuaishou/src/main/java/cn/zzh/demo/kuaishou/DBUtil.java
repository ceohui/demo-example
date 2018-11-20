package cn.zzh.demo.kuaishou;

import cn.zzh.demo.kuaishou.bean.KsHistFans;
import cn.zzh.demo.kuaishou.bean.KsHistWorks;
import cn.zzh.demo.kuaishou.bean.KsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {

	static String JDBC_URL = "jdbc:mysql://localhost:3306/live_top?characterEncoding=utf-8";
	static String JDBC_NAME = "root";
	static String JDBC_PWD = "123456";

	private static final Logger LOG = LoggerFactory.getLogger(DBUtil.class);
	private static Connection connection;

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
//		if (connection != null) {
//			return connection;
//		}
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(JDBC_URL, JDBC_NAME, JDBC_PWD);
		return connection;
	}

	public static int saveOrUpdateKsUser(KsUser user) {
		try (Connection conn = getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("insert into ks_user_info(user_id,kwai_id,name,sign,sex,head_url,num_fans,num_follow,num_works,create_time,modify_time) " +
					"values(?,?,?,?,?,?,?,?,?,now(),now()) on duplicate key update kwai_id=?,name=?,sign=?,sex=?,head_url=?,num_fans=?,num_follow=?,num_works=?,modify_time=now()");

			stmt.setString(1, user.getUserId());
			stmt.setString(2, user.getKwaiId());
			stmt.setString(3, user.getName());
			stmt.setString(4, "");
			stmt.setInt(5, user.getSex());
			stmt.setString(6, user.getHeadUrl());
			stmt.setInt(7, user.getNumFans());
			stmt.setInt(8, user.getNumFollow());
			stmt.setInt(9, user.getNumWorks());

			stmt.setString(10, user.getKwaiId());
			stmt.setString(11, user.getName());
			stmt.setString(12, "");
			stmt.setInt(13, user.getSex());
			stmt.setString(14, user.getHeadUrl());
			stmt.setInt(15, user.getNumFans());
			stmt.setInt(16, user.getNumFollow());
			stmt.setInt(17, user.getNumWorks());

			int row = stmt.executeUpdate();

			return row;
		} catch (SQLException e) {
			LOG.error("error", e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			LOG.error("error", e);
			e.printStackTrace();
		}
		return 0;
	}

	public static int saveKsHistFans(KsHistFans bean) {
		try (Connection conn = getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("insert into ks_hist_fans(user_id,kwai_id,num_fans,num_follow,num_works,create_time) " +
					"values(?,?,?,?,?,now()) ");

			stmt.setString(1, bean.getUserId());
			stmt.setString(2, bean.getKwaiId());
			stmt.setInt(3, bean.getNumFans());
			stmt.setInt(4, bean.getNumFollow());
			stmt.setInt(5, bean.getNumWorks());

			int row = stmt.executeUpdate();

			return row;
		} catch (Exception e) {
			LOG.error("error", e);
			e.printStackTrace();
		}
		return 0;
	}

	public static int saveKsHistWorks(KsHistWorks bean) {
		try (Connection conn = getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("insert into ks_hist_works(user_id,kwai_id,works_id,caption,num_view,num_like,num_common,create_time) " +
					"values(?,?,?,?,?,?,?,now()) ");

			stmt.setString(1, bean.getUserId());
			stmt.setString(2, bean.getKwaiId());
			stmt.setString(3, bean.getWorksId());
			stmt.setString(4, bean.getCaption());
			stmt.setInt(5, bean.getNumView());
			stmt.setInt(6, bean.getNumLike());
			stmt.setInt(7, bean.getNumCommon());

			int row = stmt.executeUpdate();

			return row;
		} catch (Exception e) {
			LOG.error("error", e);
			e.printStackTrace();
		}
		return 0;
	}


}
