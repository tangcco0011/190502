package com.kgc.tangcco.util.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class BaseDao {
	private BaseDao() {
	}

	private static DataSource ds;

	static {
		try {
			ds = new ComboPooledDataSource();
			System.out.println("初始化连接参数成功！");
		} catch (Exception e) {
			System.out.println("初始化连接参数失败！");
			e.printStackTrace();
		}

	}

	// 返回数据连接池
	public static DataSource getDataSource() {
		return ds;
	}

	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	public static Connection getConnection() {
		Connection conn = tl.get();
		try {
			if (conn == null) {
				conn = ds.getConnection();
				tl.set(conn);
				System.out.println("数据库创建连接成功");
			}
		} catch (SQLException e) {
			System.out.println("数据库创建连接失败");
			// e.printStackTrace();
		} finally {
			return conn;
		}
	}

	public static Statement getStatement(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			System.out.println("数据连接已建立");
		} catch (SQLException e) {
			System.out.println("数据连已建立失败");
			e.printStackTrace();
		}
		return stmt;
	}

	public static PreparedStatement getPreparedStatement(Connection conn, String sql) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			System.out.println("数据库连接后预编译SQL语句成功");
		} catch (SQLException e) {
			System.out.println("数据库连接后预编译SQL语句失败");
			// e.printStackTrace();
		}
		return pst;
	}

	public static ResultSet getResultSet(PreparedStatement pst) {
		ResultSet rs = null;
		try {
			rs = pst.executeQuery();
			System.out.println("返回结果集成功");
		} catch (SQLException e) {
			System.out.println("返回结果集失败");
			// e.printStackTrace();
		}
		return rs;
	}

	public static ResultSet getResultSet(Statement stmt, String sql) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			System.out.println("返回结果集成功");
		} catch (SQLException e) {
			System.out.println("返回结果集失败");
			// e.printStackTrace();
		}
		return rs;
	}

	public static ResultSet executeQuery(PreparedStatement pst, Object... parameters) {
		ResultSet rs = null;
		try {
			for (int i = 0; i < parameters.length; i++) {
				pst.setObject(i + 1, parameters[i]);
			}
			rs = pst.executeQuery();
		} catch (SQLException e) {
			return rs;
		}
		return rs;
	}

	public static Boolean executeUpdate(PreparedStatement pst, Object... parameters) {
		int result = 0;
		// ResultSet rs = null;
		try {
			for (int i = 0; i < parameters.length; i++) {
				pst.setObject(i + 1, parameters[i]);
			}
			result = pst.executeUpdate();
		} catch (SQLException e) {
		}
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void closeAll(Connection conn, PreparedStatement pst, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				System.out.println("关闭结果集成功");
			} catch (SQLException e) {
				System.out.println("关闭结果集失败");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (pst != null) {
			try {
				pst.close();
				System.out.println("PreparedStatement关闭成功");
			} catch (SQLException e) {
				System.out.println("PreparedStatement关闭失败");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
		}
		if (conn != null) {
			try {
				conn.close();
				System.out.println("connection关闭成功");
			} catch (SQLException e) {
				System.out.println("connection关闭失败");
				e.printStackTrace();
			}
		}
	}

	public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				System.out.println("关闭结果集成功");
			} catch (SQLException e) {
				System.out.println("关闭结果集失败");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
				System.out.println("PreparedStatement关闭成功");
			} catch (SQLException e) {
				System.out.println("PreparedStatement关闭失败");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
		}
		if (conn != null) {
			try {
				conn.close();
				System.out.println("connection关闭成功");
			} catch (SQLException e) {
				System.out.println("connection关闭失败");
				e.printStackTrace();
			}
		}
	}

	public static void close() {
		if (tl.get() != null) {
			// 提交当前线程上绑定的 Connection
			Connection conn = getConnection();
			try {
				conn.close();
				System.out.println("断开连接成功");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("关闭连接失败");
				e.printStackTrace();
			}
			// 将连接从 tl 中移除 移除当前线程绑定的 Connection
			tl.remove();
		}
	}

	// 开启事务
	public static void startTransaction() {
		// 为当前线程绑定的Connection 开启事务
		Connection conn = getConnection();
		try {
			conn.setAutoCommit(false);
			System.out.println("事务开启成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("事务开启失败");
			e.printStackTrace();
		}
	}

	// 提交事务
	public static void commit() {
		// 判断一下线程上是否有连接
		if (tl.get() != null) {
			// 提交当前线程上绑定的 Connection
			Connection conn = getConnection();
			try {
				conn.commit();
				System.out.println("事务提交成功");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("事务提交失败");
				e.printStackTrace();
			}
		}
	}
}
