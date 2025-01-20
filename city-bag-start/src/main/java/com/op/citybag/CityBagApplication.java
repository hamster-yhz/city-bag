package com.op.citybag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CityBagApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityBagApplication.class, args);
        printCustomLogo();

    }

//    @Bean
//    public WebClient webClient() {
//        HttpClient httpClient = HttpClient.create()
//                .proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
//                        .host("localhost")
//                        .port(7897));
//
//        return WebClient.builder()
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
//                .build();
//    }

//    @Bean
//    public RestTemplate restTemplate() {
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 7897));
//        requestFactory.setProxy(proxy);
//        return new RestTemplate(requestFactory);
//    }

    private static void printCustomLogo() {
        System.out.println("                  鸡 你 太 美                      ");
        System.out.println("      ____ _  __     _       __  __     _       __  ");
        System.out.println("     / __ \\ |/ /    | |     / / / /    | |     / /  ");
        System.out.println("    / /_/ /   /     | | /| / / / /     | | /| / /   ");
        System.out.println("   / ____/   |      | |/ |/ / / /___   | |/ |/ /    ");
        System.out.println("  /_/   /_/|_|      |__/|__/ /_____/   |__/|__/     ");
        System.out.println();

        System.out.println("              city-bag 小程序后台启动成功     ");
        System.out.println("      ___ __ __    _         __        _      _ ");
        System.out.println("     / ___ ___/   (_)    ___/ /___     \\ \\  / /  ");
        System.out.println("    / /           | |   /__  ____/      \\ \\/ /   ");
        System.out.println("   / /___ ___     | |     / /___          / /    ");
        System.out.println("  /__ ___ __/     |_|    /_____/         /_/     ");
        System.out.println();


    }
}
