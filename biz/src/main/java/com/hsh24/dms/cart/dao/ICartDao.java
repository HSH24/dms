package com.hsh24.dms.cart.dao;

import java.util.List;

import com.hsh24.dms.api.cart.bo.Cart;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ICartDao {

	/**
	 * 
	 * @param cart
	 * @return
	 */
	int createCart(Cart cart);

	/**
	 * 
	 * @param cart
	 * @return
	 */
	int checkCart(Cart cart);

	/**
	 * 
	 * @param cart
	 * @return
	 */
	int getCartCount(Cart cart);

	/**
	 * 
	 * @param cart
	 * @return
	 */
	List<Cart> getCartList(Cart cart);

	/**
	 * 
	 * @param cart
	 * @return
	 */
	int updateCart(Cart cart);

	/**
	 * 
	 * @param cart
	 * @return
	 */
	int updateQuantity(Cart cart);

}
