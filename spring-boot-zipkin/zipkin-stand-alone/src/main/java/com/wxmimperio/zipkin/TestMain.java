package com.wxmimperio.zipkin;

import brave.Tracing;
import brave.context.log4j2.ThreadContextCurrentTraceContext;
import brave.http.HttpTracing;
import brave.okhttp3.TracingInterceptor;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.sampler.Sampler;
import zipkin2.Endpoint;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

import java.io.IOException;

import static brave.Span.Kind.CLIENT;

public class TestMain {

    public static void main(String[] args) throws Exception {
        Sender sender = OkHttpSender.create("http://127.0.0.1:9411/api/v2/spans");
        AsyncReporter<Span> spanReporter = AsyncReporter.create(sender);

        Tracing tracing = Tracing.newBuilder()
                .localServiceName("stand-alone-service")
                .spanReporter(spanReporter)
                .sampler(Sampler.ALWAYS_SAMPLE)
                .propagationFactory(ExtraFieldPropagation.newFactory(B3Propagation.FACTORY, "user-name"))
                .currentTraceContext(ThreadContextCurrentTraceContext.create())
                .supportsJoin(true)
                .build();
        HttpTracing httpTracing = HttpTracing.create(tracing);


        brave.Span span = tracing.tracer().newTrace().name("stand-alone-service");
        span.start();
        span.tag("test", "stand-alone-service");
        span.annotate("Method In");

        okHttpTest(httpTracing);

        span.annotate("Method Out");

        span.finish();

        /*brave.Span span2 = tracing.tracer().newChild(span.context()).name("get").kind(CLIENT).start();
        span2.tag("clnt/finagle.version", "6.36.0");
        span2.remoteEndpoint(Endpoint.newBuilder()
                .serviceName("backend")
                .ip("localhost")
                .port(8082).build());

   *//*     span2.annotate(Constants.WIRE_SEND);
        span2.annotate(Constants.WIRE_RECV);*//*

        span2.finish();*/

        tracing.close();
        spanReporter.close();
        sender.close();
    }

    private static void okHttpTest(HttpTracing httpTracing) {
        String url = "http://localhost:8082";
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(TracingInterceptor.create(httpTracing)).build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
