package com.memcoupon.model;

import java.util.List;

public interface MemCouponDAO_interface {
	
//	-- 新增一筆會員優惠券資料
	public void insert(MemCouponVO memCouponVO);
//	-- 更改 會員擁有的優惠券 資料內容
	public void update(MemCouponVO memCouponVO);
//	-- 找出 某個會員 擁有的所有優惠券
	public List<MemCouponVO> findMemCouponByMemID(Integer memID);
//	-- 找出 某個會員 已使用(未使用、過期的)的所有優惠券
	public List<MemCouponVO> findMemCouponByStatus(Integer memID, Integer coupStatus);
//	-- 找出 某個會員 擁有的某種優惠券
	public List<MemCouponVO> findMemCouponByCoupTypeNo(Integer memID, Integer coupTypeNo);
//	-- 找出 某個會員 剩3天要到期的優惠券
	
//	-- 找出 所有會員 擁有的優惠券
	public List<MemCouponVO> getAll();
}
