package com.csi.sbs.sysadmin.business.util;

import com.csi.sbs.common.business.model.SendMessageModel;
import com.csi.sbs.common.business.util.LogLevelUtil;
import com.csi.sbs.common.business.util.SendMsgUtil;

public class SendLogUtil {
	
	/****
	 * 日志级别 OFF>FATAL>ERROR>WARN>INFO>DEBUG>TRACE>ALL
	 */
	
	private static String OFF = "OFF";
	private static String ERROR = "ERROR";
	private static String WARN = "WARN";
	private static String INFO = "INFO";
	private static String DEBUG = "DEBUG";
	private static SendMessageModel smm = new SendMessageModel();
	
	public static void send(String topic,String msg) throws Exception{
		//判断日志级别
		if(LogLevelUtil.getLevel().equals(INFO)){
			//给kafka发送消息
	        smm.setTopic(topic);
	        smm.setMessage(msg);
	        SendMsgUtil.sendMsg(smm);
		}
		if(LogLevelUtil.getLevel().equals(ERROR)){
			//给kafka发送消息
	        smm.setTopic(topic);
	        smm.setMessage(msg);
	        SendMsgUtil.sendMsg(smm);
		}
		if(LogLevelUtil.getLevel().equals(DEBUG)){
			//给kafka发送消息
	        smm.setTopic(topic);
	        smm.setMessage(msg);
	        SendMsgUtil.sendMsg(smm);
		}
		if(LogLevelUtil.getLevel().equals(WARN)){
			//给kafka发送消息
	        smm.setTopic(topic);
	        smm.setMessage(msg);
	        SendMsgUtil.sendMsg(smm);
		}
		if(LogLevelUtil.getLevel().equals(OFF)){
			//不发送消息
		}
	}

}
