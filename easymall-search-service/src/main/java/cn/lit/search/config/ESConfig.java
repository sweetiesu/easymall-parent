package cn.lit.search.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "es")
public class ESConfig {
    private List<String> nodes;
    //初始化方法 初始化创建Transportclient
    @Bean
    public TransportClient initClient(){
        TransportClient client=new PreBuiltTransportClient(Settings.EMPTY);
        //封装nodes数据
        try{
            for(String node:nodes){
                //10.9.39.13:9300
                String host=node.split(":")[0];
                int port=Integer.parseInt(node.split(":")[1]);
                InetSocketTransportAddress address=
                        new InetSocketTransportAddress(
                                InetAddress.getByName(host),port
                        );
                client.addTransportAddresses(address);
            }
            return client;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}
