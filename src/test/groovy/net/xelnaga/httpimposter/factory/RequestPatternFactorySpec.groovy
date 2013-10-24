package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.model.RequestPattern
import net.xelnaga.httpimposter.model.DefaultHttpHeader
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

class RequestPatternFactorySpec extends Specification {
    
    RequestPatternFactory factory

    HttpHeaderFilter mockHttpHeaderFilter
    
    void setup() {

        factory = new RequestPatternFactory()
        
        mockHttpHeaderFilter = Mock(HttpHeaderFilter)
        factory.filter = mockHttpHeaderFilter
    }
    
    def 'from http request'() {
        
        given:
            MockHttpServletRequest httpRequest = new MockHttpServletRequest(
                    requestURI: '/fruity/pineapple',
                    method: 'mango',
                    contentType: 'text/banana',
                    content: 'qwerty'.bytes
            )
            httpRequest.addHeader('Pineapple', 'Passionfruit')
            httpRequest.addHeader('Durian', 'Stinky')

        when:
            RequestPattern requestPattern = factory.fromHttpRequest(httpRequest)

        then:
            1 * mockHttpHeaderFilter.isMatchable(new DefaultHttpHeader('Content-Type', 'text/banana')) >> true
            1 * mockHttpHeaderFilter.isMatchable(new DefaultHttpHeader('Pineapple', 'Passionfruit')) >> true
            1 * mockHttpHeaderFilter.isMatchable(new DefaultHttpHeader('Durian', 'Stinky')) >> false
            0 * _._

        and:
            requestPattern == new RequestPattern(
                    uri: '/fruity/pineapple',
                    method: 'mango',
                    headers: [
                            new DefaultHttpHeader('Content-Type', 'text/banana'),
                            new DefaultHttpHeader('Pineapple', 'Passionfruit')
                    ],
                    body: 'qwerty'
            )
    }

    def 'from http request with query string'() {

        given:
            MockHttpServletRequest httpRequest = new MockHttpServletRequest(
                    requestURI: '/fruity/pineapple',
                    queryString: 'peaches=true&oranges=false',
                    method: 'mango',
                    contentType: 'text/banana',
                    content: 'qwerty'.bytes
            )
            httpRequest.addHeader('Pineapple', 'Passionfruit')
            httpRequest.addHeader('Durian', 'Stinky')

        when:
            RequestPattern requestPattern = factory.fromHttpRequest(httpRequest)

        then:
            1 * mockHttpHeaderFilter.isMatchable(new DefaultHttpHeader('Content-Type', 'text/banana')) >> true
            1 * mockHttpHeaderFilter.isMatchable(new DefaultHttpHeader('Pineapple', 'Passionfruit')) >> true
            1 * mockHttpHeaderFilter.isMatchable(new DefaultHttpHeader('Durian', 'Stinky')) >> false
            0 * _._

        and:
            requestPattern == new RequestPattern(
                    uri: '/fruity/pineapple?peaches=true&oranges=false',
                    method: 'mango',
                    headers: [
                            new DefaultHttpHeader('Content-Type', 'text/banana'),
                            new DefaultHttpHeader('Pineapple', 'Passionfruit')
                    ],
                    body: 'qwerty'
            )
    }
}
