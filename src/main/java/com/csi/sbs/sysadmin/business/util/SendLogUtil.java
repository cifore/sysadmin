package com.csi.sbs.sysadmin.business.util;

import com.csi.sbs.common.business.model.SendMessageModel;
import com.csi.sbs.common.business.util.ReadLogConfigUtil;
import com.csi.sbs.common.business.util.SendMsgUtil;

public class SendLogUtil {

	/****
	 * 日志级别 OFF>FATAL>ERROR>WARN>INFO>DEBUG>TRACE>ALL
	 */

	@SuppressWarnings("unused")
	private static final int OFF = 0;
	private static final int FATAL = 50000;
	private static final int ERROR = 40000;
	private static final int WARN = 30000;
	private static final int INFO = 20000;
	private static final int DEBUG = 10000;
	private static final SendMessageModel smm = new SendMessageModel();
	private static final String TOPIC = ReadLogConfigUtil.lcm.getTopic();
	private static final int LEVEL = ReadLogConfigUtil.lcm.getLevel();

	public static void sendFatal(String msg) throws Exception {
		if(FATAL>=LEVEL){
			// 给kafka发送消息
			smm.setTopic(TOPIC);
			smm.setMessage("5"+msg);
			SendMsgUtil.sendMsg(smm);
		}
	}

	public static void sendError(String msg) throws Exception {
		if(ERROR>=LEVEL){
			// 给kafka发送消息
			smm.setTopic(TOPIC);
			smm.setMessage("4"+msg);
			SendMsgUtil.sendMsg(smm);
		}
	}

	public static void sendWarn(String msg) throws Exception {
		if(WARN>=LEVEL){
			// 给kafka发送消息
			smm.setTopic(TOPIC);
			smm.setMessage("3"+msg);
			SendMsgUtil.sendMsg(smm);
		}
	}

	public static void sendInfo(String msg) throws Exception {
		if(INFO>=LEVEL){
			// 给kafka发送消息
			smm.setTopic(TOPIC);
			smm.setMessage("2"+msg);
			SendMsgUtil.sendMsg(smm);
		}
	}

	public static void sendDebug(String msg) throws Exception {
		if(DEBUG>=LEVEL){
			// 给kafka发送消息
			smm.setTopic(TOPIC);
			smm.setMessage("1"+msg);
			SendMsgUtil.sendMsg(smm);
		}
	}
}
