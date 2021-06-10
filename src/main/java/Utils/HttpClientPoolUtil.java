package Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import Utils.HttpClientResult;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * commons-httpclient(停更)与httpclient(继续升级中)
 *
 * HTTP连接池请求,支持http和https请求,
 * <p>
 * 基于org.apache.httpcomponents.httpcore<version>4.4.10</version>
 * </p>
 * <p>
 * 基于org.apache.httpcomponents.httpclient<version>4.5.6</version>
 * </p>
 *
 * @author Henry(fba02)
 * @version [版本号, 2019年12月8日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HttpClientPoolUtil {
    private static final String ENCODING = "UTF-8";
    public static final int DEFAULT_CONNECT_TIMEOUT = 6000;
    public static final int DEFAULT_READ_TIMEOUT = 6000;
    public static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 6000;
    private static final int MAX_TOTAL = 64;
    private static final int MAX_PER_ROUTE = 32;
    private static final RequestConfig requestConfig;
    private static final PoolingHttpClientConnectionManager connectionManager;
    private static final HttpClientBuilder httpBuilder;
    private static final CloseableHttpClient httpClient;
    private static final CloseableHttpClient httpsClient;
    private static SSLContext sslContext;

    static {
        try {
            sslContext = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[] {tm}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_READ_TIMEOUT).setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT).build();
        @SuppressWarnings("deprecation")
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))
                .build();
        connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setMaxTotal(MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        httpBuilder = HttpClientBuilder.create();
        httpBuilder.setDefaultRequestConfig(requestConfig);
        httpBuilder.setConnectionManager(connectionManager);
        httpClient = httpBuilder.build();
        httpsClient = httpBuilder.build();
    }

    /**
     * GET
     *
     * @param url
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult doGet(String url)
            throws Exception {
        return doGet(url, false);
    }

    /**
     * GET
     *
     * @param url
     * @param https
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult doGet(String url, boolean https)
            throws Exception {
        return doGet(url, null, null, https);
    }

    /**
     * GET
     *
     * @param url
     * @param params
     * @param https
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult doGet(String url, Map<String, String> params, boolean https)
            throws Exception {
        return doGet(url, null, params, https);
    }

    /**
     * GET
     *
     * @param url
     * @param headers
     * @param params
     * @param https
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params, boolean https)
            throws Exception {
        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        // 创建HTTP对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setConfig(requestConfig);
        // 设置请求头
        setHeader(headers, httpGet);
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        try {
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpGet);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpGet);
            }
        } finally {
            httpGet.releaseConnection();
            release(httpResponse);
        }
    }

    /**
     * POST不带参数
     *
     * @param url
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult doPost(String url)
            throws Exception {
        return doPost(url, Boolean.FALSE);
    }

    /**
     * @param url
     * @param https
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult doPost(String url, boolean https)
            throws Exception {
        return doPost(url, null, (Map<String, String>)null, https);
    }

    /**
     * 带请求参数
     *
     * @param url
     * @param params
     * @param https
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult doPost(String url, Map<String, String> params, boolean https)
            throws Exception {
        return doPost(url, null, params, https);
    }

    /**
     * POST
     *
     * @param url
     * @param headers
     * @param params
     * @param https
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult doPost(String url, Map<String, String> headers, Map<String, String> params, boolean https)
            throws Exception {
        // 创建HTTP对象
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        // 设置请求头
        setHeader(headers, httpPost);
        // 封装请求参数
        setParam(params, httpPost);
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        try {
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpPost);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpPost);
            }
        } finally {
            httpPost.releaseConnection();
            release(httpResponse);
        }
    }

    /**
     * POST请求JSON
     *
     * @param url
     * @param headers
     * @param json
     * @param https
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult doPost(String url, Map<String, String> headers, String json, boolean https)
            throws Exception {
        // 创建HTTP对象
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        // 设置请求头
        setHeader(headers, httpPost);
        StringEntity stringEntity = new StringEntity(json, ENCODING);
        stringEntity.setContentEncoding(ENCODING);
        httpPost.setEntity(stringEntity);
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        try {
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpPost);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpPost);
            }
        } finally {
            httpPost.releaseConnection();
            release(httpResponse);
        }
    }


    /**
     * 发送put请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPut(String url)
            throws Exception {
        return doPut(url);
    }

    /**
     * 发送put请求；带请求参数
     *
     * @param url 请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPut(String url, Map<String, String> params)
            throws Exception {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        // RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPut.setConfig(requestConfig);
        setParam(params, httpPut);
        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpPut);
        } finally {
            httpPut.releaseConnection();
            release(httpResponse);
        }
    }

    /**
     * 不带请求参数
     *
     * @param url
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult doDelete(String url)
            throws Exception {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        // RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpDelete.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpDelete);
        } finally {
            httpDelete.releaseConnection();
            release(httpResponse);
        }
    }

    /**
     * 带请求参数
     *
     * @param url
     * @param params
     * @param https
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult doDelete(String url, Map<String, String> params, boolean https)
            throws Exception {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.put("_method", "delete");
        return doPost(url, params, https);
    }

    /**
     * 设置封装请求头
     *
     * @param params
     * @param httpMethod
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static void setHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        /*if (MapUtils.isNotEmpty(params)) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }*/
    }

    /**
     * 封装请求参数
     *
     * @param params
     * @param httpMethod
     * @throws UnsupportedEncodingException
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static void setParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        // 封装请求参数
        /*if (MapUtils.isNotEmpty(params)) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        }*/
    }

    /**
     * 获得响应结果
     *
     * @param httpResponse
     * @param httpClient
     * @param httpMethod
     * @return
     * @throws Exception
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient, HttpRequestBase httpMethod)
            throws Exception {
        // 执行请求
        httpResponse = httpClient.execute(httpMethod);
        // 获取返回结果
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            String content = "";
            if (httpResponse.getEntity() != null) {
                content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            }
            return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
        }
        return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * 释放资源
     *
     * @param httpResponse
     * @throws IOException
     * @author Henry(fba02)
     * @version [版本号, 2019年12月8日]
     * @see [类、类#方法、类#成员]
     */
    public static void release(CloseableHttpResponse httpResponse)
            throws IOException {
        // 释放资源
        if (httpResponse != null) {
            httpResponse.close();
        }
    }
}
