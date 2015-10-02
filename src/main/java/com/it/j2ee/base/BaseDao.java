
/**    
 * Copyright (C),Kingmed
 * @FileName: BaseDao.java  
 * @Package: com.kingmed.cs.modules.workorder.persistence.dao  
 * @Description: //模块目的、功能描述  
 * @Author Liusu  
 * @Date 2014年8月30日 上午9:58:48  
 * @History: //修改记录
 * 〈author〉      〈time〉      〈version〉       〈desc〉
 * 修改人姓名            修改时间            版本号              描述   
*/  

package com.it.j2ee.base;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;



/**  
 * 数据库操作基类
 * 
 * @author liusu
 * @create 2014年8月30日 上午9:58:48 
 * @version 1.0.0
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@NoRepositoryBean
public interface BaseDao<T> extends JpaSpecificationExecutor<T>,PagingAndSortingRepository<T, Long>{

}
