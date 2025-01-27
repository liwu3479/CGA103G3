package com.actfav.model;

import static common_35.Common.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActFavJDBCDAO implements ActFavDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";

	private static final String INSERT_STMT = 
		"insert into actfavorites (MemID, ActID, ActFavDate) "
		+ "values (?, ?, Now())";
	private static final String GET_ALL_STMT = 
		"select MemID, ActID, ActFavDate "
		+ "from actfavorites order by MemID";
	private static final String GET_ONE_STMT = 
		"select MemID, ActID, ActFavDate "
		+ "from actfavorites where MemID = ?";
	private static final String DELETE = 
		"delete from actfavorites where MemID = ? and ActID = ?";
	private static final String UPDATE = 
		"update actfavorites set ActFavDate=Now() where MemID = ? and ActID = ?";
	
	
	@Override
	public void insert(ActFavVO actFavVO) {
		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(INSERT_STMT)) {
				Class.forName(driver);
				
				pstmt.setInt(1, actFavVO.getMemID());
				pstmt.setInt(2, actFavVO.getActID());
				pstmt.executeUpdate();

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			}		
	}

	@Override
	public void update(ActFavVO actFavVO) {
		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(UPDATE)) {
				Class.forName(driver);

				pstmt.setInt(1, actFavVO.getMemID());
				pstmt.setInt(2, actFavVO.getActID());

				pstmt.executeUpdate();

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			}		
	}

	@Override
	public void delete(Integer memID, Integer actID) {
		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(DELETE)) {
				Class.forName(driver);

				pstmt.setInt(1, memID);
				pstmt.setInt(2, actID);

				pstmt.executeUpdate();

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			}	
	}

	@Override
	public List<ActFavVO> findByPrimaryKey(Integer memID) {
		List<ActFavVO> listFav = new ArrayList<ActFavVO>();
		ActFavVO actFavVO = null;

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(GET_ONE_STMT);
				) {
				Class.forName(driver);

			pstmt.setInt(1, memID);
			ResultSet rs = pstmt.executeQuery();
			

			while (rs.next()) {
				// actFacVo 也稱為 Domain objects
				actFavVO = new ActFavVO();
				actFavVO.setMemID(rs.getInt("memID"));
				actFavVO.setActID(rs.getInt("actID"));
				actFavVO.setActFavDate(rs.getTimestamp("actFavDate"));
				listFav.add(actFavVO);
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}
		return listFav;
	}

	@Override
	public List<ActFavVO> getAll() {
		List<ActFavVO> list = new ArrayList<ActFavVO>();
		ActFavVO actFavVO = null;

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(GET_ALL_STMT);
				) {
				Class.forName(driver);
				ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// actFavVO 也稱為 Domain objects
				actFavVO = new ActFavVO();
				actFavVO.setMemID(rs.getInt("memID"));
				actFavVO.setActID(rs.getInt("actID"));
				actFavVO.setActFavDate(rs.getTimestamp("actFavDate"));
				list.add(actFavVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}
		return list;
	}
	
	public static void main(String[] args) {

		ActFavJDBCDAO dao = new ActFavJDBCDAO();

		// 新增
		ActFavVO actFavVO1 = new ActFavVO();
		actFavVO1.setMemID(11001);
		actFavVO1.setActID(61001);
		dao.insert(actFavVO1);

		// 修改
		ActFavVO actFavVO2 = new ActFavVO();
		actFavVO2.setMemID(11001);
		actFavVO2.setActID(61001);
		dao.update(actFavVO2);
		
		// 刪除
		dao.delete(11001, 61001);

		// 查詢 會員所收藏的活動
		List<ActFavVO> listFav = dao.findByPrimaryKey(11001);
		for (ActFavVO aListFav : listFav) {
			System.out.print(aListFav.getMemID() + ",");
			System.out.print(aListFav.getActID() + ",");
			System.out.println(aListFav.getActFavDate());
			System.out.println("---------------------");
		}
		
		// 查詢
		List<ActFavVO> list = dao.getAll();
		for (ActFavVO aActFav : list) {
			System.out.print(aActFav.getMemID() + ",");
			System.out.print(aActFav.getActID() + ",");
			System.out.print(aActFav.getActFavDate());
			System.out.println();
		}
	}
}
