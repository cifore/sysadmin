package com.csi.sbs.sysadmin.business.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.AddUserModel;
import com.csi.sbs.sysadmin.business.clientmodel.DockerModel;
import com.csi.sbs.sysadmin.business.clientmodel.PermissionModel;
import com.csi.sbs.sysadmin.business.clientmodel.SandBoxModel;
import com.csi.sbs.sysadmin.business.exception.NotFoundException;
import com.csi.sbs.sysadmin.business.exception.OtherException;
import com.csi.sbs.sysadmin.business.service.PermissionService;
import com.csi.sbs.sysadmin.business.service.SandboxService;
import com.csi.sbs.sysadmin.business.service.UserBranchService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/permission")
public class AuthorityController {

	@Resource
	private PermissionService permissionService;
	
	@Resource
	private UserBranchService userBranchService;
	
	@Resource
	private SandboxService sandboxService;
	
	@Resource
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 权限验证
	 * 
	 * @param addUserModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/permissionValidate", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to validate permission", notes = "version 0.0.1")
	@ApiIgnore()
	public String permissionValidate(PermissionModel permissionModel) throws Exception {
		try {
			return objectMapper.writeValueAsString(permissionService.validate(permissionModel));
		} catch (Exception e) {
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}
	
	/**
	 * 授权developer 可以访问哪个银行的数据
	 * @param addUserModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/developerAuthorization", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = " This API is designed to authorize developers to use LBS API.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
		@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
		@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
		@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
		@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."),
    })
	public ResultUtil developerAuthorization(@RequestBody @Validated AddUserModel addUserModel) throws Exception {
		try {
			return permissionService.userAuthorize(restTemplate, addUserModel);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * 给developer指派沙盒
	 * @param sbm
	 * @param request
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getSandBox", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to get sandBoxId for developer.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil getSandBox(@RequestBody SandBoxModel sandBoxModel,HttpServletRequest request) throws Exception{
		try{
			return sandboxService.randomOne(sandBoxModel);
			//return userBranchService.appSandBoxForDeveloper(sandBoxModel, restTemplate);
		}catch(OtherException e){
			throw e;
		}catch(NotFoundException e){
			throw e;
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 给developer设置dockerId
	 * @param dm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/appDockerForDeveloper", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to set dockerId for developer.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil appDockerForDeveloper(@RequestBody DockerModel dockerModel,HttpServletRequest request) throws Exception{
		try{
			return userBranchService.appDockerForDeveloper(dockerModel, restTemplate);
		}catch(OtherException e){
			throw e;
		}catch(NotFoundException e){
			throw e;
		}catch(Exception e){
			throw e;
		}
	}
	
	
//	/**
//	 * 线下生成数据
//	 * @param sbm
//	 * @param request
//	 * @throws Exception 
//	 */
//	@SuppressWarnings("rawtypes")
//	@RequestMapping(value = "/getSandBox", method = RequestMethod.POST)
//	@ResponseBody
//	@ApiOperation(value = "This API is designed to get sandBoxId for developer.", notes = "version 0.0.1")
//	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
//			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
//			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
//			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
//			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
//	public ResultUtil getSandBox(@RequestBody SandBoxModel sandBoxModel,HttpServletRequest request) throws Exception{
//		try{
//			return sandboxService.randomOne(sandBoxModel);
//			//return userBranchService.appSandBoxForDeveloper(sandBoxModel, restTemplate);
//		}catch(OtherException e){
//			throw e;
//		}catch(NotFoundException e){
//			throw e;
//		}catch(Exception e){
//			throw e;
//		}
//	}

}
