package nl.onnoh.baton.util;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

public class HttpClient {

    public static void main(String[] args) {

        final Arguments arguments = parseArgumentsOrExit(args);

        final java.net.http.HttpClient client = java.net.http.HttpClient.newBuilder().followRedirects(java.net.http.HttpClient.Redirect.NORMAL).build();

        final List<CompletableFuture<HttpResponse<Void>>> responseFutures = Stream.generate(
                        () -> makeRequest(arguments.uri)).limit(arguments.count)
                .map(r -> client.sendAsync(r, HttpResponse.BodyHandlers.discarding()))
                .collect(toList());

        CompletableFuture.allOf(responseFutures.toArray(CompletableFuture[]::new)).join();

        responseFutures.stream()
                .map(rf -> rf.getNow(null))
                .filter(Objects::nonNull)
                .forEach(response -> System.out.println(response.statusCode()));
    }

    private static HttpRequest makeRequest(final String uri) {
        return HttpRequest.newBuilder().GET().uri(URI.create(uri)).build();
    }

    private static Arguments parseArgumentsOrExit(final String[] args) {
        switch (args.length) {
            case 1:
                return new Arguments(args[0]);
            case 2:
                return new Arguments(args[0], parseInt(args[1]));
            default:
                System.err.println("error: arguments were incorrect");
                System.err.println("usage: http-client URI COUNT");
                System.exit(1);
                return null;
        }
    }
    private static final class Arguments {

        final String uri;

        final int count;

        Arguments(final String uri) {
            this.uri = uri;
            this.count = 10;
        }

        Arguments(final String uri, final int count) {
            this.uri = uri;
            this.count = count;
        }
    }

}
