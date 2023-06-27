package de.qaware.cloudcomputing.parse;

import com.github.amsacode.predict4java.TLE;
import de.qaware.cloudcomputing.tle.TleMember;
import io.opentelemetry.instrumentation.annotations.WithSpan;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TleParser {

    @WithSpan
    public TLE parseTLE(TleMember tleMember) {
        if (tleMember == null) {
            throw new IllegalArgumentException("tleMember");
        }

        return new TLE(new String[]{tleMember.getName(), tleMember.getLine1(), tleMember.getLine2()});
    }

}
