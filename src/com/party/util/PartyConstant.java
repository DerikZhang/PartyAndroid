package com.party.util;

public class PartyConstant {

	/**
	 * 记录成功登陆的用户账号
	 */
	public static String userPhoneNumber;

	/**
	 * phoneNumberLength=11;
	 */
	public static final int PHONE_NUMBER_LENGTH = 11;

	/**
	 * 验证码为6个数字,identifyingCodeLength = 6;
	 */
	public static final int IDENTIFYING_CODE_LENGTH = 6;

	/**
	 * 服务器的域名
	 */
	public static final String URI_SERVER = "http://changxiangju.duapp.com";

	/**
	 * 服务器发送验证码的目录
	 */
	public static final String URI_DIRECTORY_REGISTER = "/SendVerificationCode";

	/**
	 * 校验验证码的目录
	 */
	public static final String URI_DIRECTORY_SIGN = "/sign/Android";

	/**
	 * 用户登陆目录
	 */
	public static final String URI_DIRECTORY_LOGIN = "/login/Android";

	/**
	 * 创建Party,后面加上PartID和用户ID——/PartyID/u_id
	 */
	public static final String URI_DEIRECTORY_CREATE_PARTY = "/Party/CreateParty";

	/**
	 * 加入Party,
	 */
	public static final String URI_DEIRECTORY_JOIN_PARTY_IN_ADNROID = "/Party/JoinPartyInAndroid";

	/**
	 * 修改用户信息
	 */
	public static final String URI_DEIRECTORY_EDIT_USER_INFO = "/Party/InParty/EditUserInfo";

	/**
	 * 记录用户是否成功注册success: registered=true,failure: registered=false;
	 */
	public static boolean registered = false;

	/**
	 * 设置密码是否成功,success: SetPassword=true,failure: SetPassword=false;
	 */
	public static boolean SetPassword = false;

	/**
	 * user ID
	 */
	public static int u_id;

	/**
	 * party ID
	 */
	public static int p_id;
}
