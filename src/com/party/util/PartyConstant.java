package com.party.util;

public class PartyConstant {

	/**
	 * ��¼�ɹ���½���û��˺�
	 */
	public static String userPhoneNumber;

	/**
	 * phoneNumberLength=11;
	 */
	public static final int PHONE_NUMBER_LENGTH = 11;

	/**
	 * ��֤��Ϊ6������,identifyingCodeLength = 6;
	 */
	public static final int IDENTIFYING_CODE_LENGTH = 6;

	/**
	 * ������������
	 */
	public static final String URI_SERVER = "http://changxiangju.duapp.com";

	/**
	 * ������������֤���Ŀ¼
	 */
	public static final String URI_DIRECTORY_REGISTER = "/SendVerificationCode";

	/**
	 * У����֤���Ŀ¼
	 */
	public static final String URI_DIRECTORY_SIGN = "/sign/Android";

	/**
	 * �û���½Ŀ¼
	 */
	public static final String URI_DIRECTORY_LOGIN = "/login/Android";

	/**
	 * ����Party,�������PartID���û�ID����/PartyID/u_id
	 */
	public static final String URI_DEIRECTORY_CREATE_PARTY = "/Party/CreateParty";

	/**
	 * ����Party,
	 */
	public static final String URI_DEIRECTORY_JOIN_PARTY_IN_ADNROID = "/Party/JoinPartyInAndroid";

	/**
	 * �޸��û���Ϣ
	 */
	public static final String URI_DEIRECTORY_EDIT_USER_INFO = "/Party/InParty/EditUserInfo";

	/**
	 * ��¼�û��Ƿ�ɹ�ע��success: registered=true,failure: registered=false;
	 */
	public static boolean registered = false;

	/**
	 * ���������Ƿ�ɹ�,success: SetPassword=true,failure: SetPassword=false;
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
