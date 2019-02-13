package com.ftibw.config;

import com.ftibw.service.IamgateWebService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @author : Ftibw
 * @date : 2019/2/13 9:10
 */
@Configuration
public class WebServiceConfig {

    /**
     * 自定义services目录名称时,方法名不能为dispatcherServlet,否则会使其他接口转发无效
     * 不修改目录时,不用新建该Bean
     * 默认urlMapping为/services/*
     */
    @Bean
    public ServletRegistrationBean disServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/iamgate/services/*");
    }

    /**
     * localhost无法访问<p>http://IP:PORT/iamgate/services/IamgateWebService?wsdl</p>时,
     * 可使127.0.0.1或者通过ifconfig或者ipconfig查看IP进行访问
     */
    @Bean
    public Endpoint endpoint(Bus bus, IamgateWebService iamgateWebService) {
        //绑定要发布的服务
        EndpointImpl endpoint = new EndpointImpl(bus, iamgateWebService);
        //显示要发布的名称
        endpoint.publish("/IamgateWebService");
        return endpoint;
    }
}
