package com.hsh24.dms.api.cart;

import java.util.List;

import com.hsh24.dms.api.cart.bo.Cart;
import com.hsh24.dms.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ICartService {

	String STATE_FINISH = "E";

	String STATE_REMOVE = "D";

	/**
	 * 
	 * @param userId
	 * @param itemId
	 * @param skuId
	 * @param quantity
	 * @return
	 */
	BooleanResult createCart(Long userId, String itemId, String skuId, String quantity);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	int getCartCount(Long userId);

	/**
	 * 获取用户购物车.
	 * 
	 * @param userId
	 * @return
	 */
	List<Cart> getCartList(Long userId);

	/**
	 * 获取用户购物车.
	 * 
	 * @param userId
	 * @param cartId
	 * @return
	 */
	List<Cart> getCartList(Long userId, String[] cartId);

	/**
	 * 移除购物车.
	 * 
	 * @param userId
	 * @param cartId
	 * @return
	 */
	BooleanResult removeCart(Long userId, String[] cartId);

	/**
	 * 
	 * @param userId
	 * @param cartId
	 * @param quantity
	 * @return
	 */
	BooleanResult updateQuantity(Long userId, String cartId, String quantity);

	/**
	 * 根据购物车完成订单.
	 * 
	 * @param userId
	 * @param cartId
	 * @return
	 */
	BooleanResult finishCart(Long userId, String[] cartId);

}
