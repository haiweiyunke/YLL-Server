package yll.common.exception;

/**
 * 
	 * 项目名称：ksh  
	 * 类名称：DefinitionException   
	 * 类描述：   创建自定义异常
	 * 创建人：lisw
	 * 创建时间：2015-12-7 下午4:28:46   
	 * 修改人：
	 * 修改时间：2015-12-7 下午4:28:46   
	 * 修改备注：   
	 * @version
 */
public class DefinitionException extends Exception {
	private static final long serialVersionUID = -8470876653310450339L;

	/**
	 * 
	 * @param msg //异常的描述
	 */
	public DefinitionException(String msg){
		super(msg);//调用父类有参的构造方法
	}

}
