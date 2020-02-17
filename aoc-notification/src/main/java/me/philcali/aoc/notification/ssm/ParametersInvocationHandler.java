package me.philcali.aoc.notification.ssm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import com.amazonaws.services.simplesystemsmanagement.model.ParameterNotFoundException;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;

@Data @Builder
public interface ParametersInvocationHandler extends InvocationHandler {
    Logger LOGGER = LoggerFactory.getLogger(ParametersInvocationHandler.class);

    Map<String, Supplier<Object>> providers();

    AWSSimpleSystemsManagement ssm();

    String pathPrefix();

    boolean decryption();

    @Override
    default public Object invoke(final Object proxy, final Method method, Object[] params) throws Throwable {
        if (providers().containsKey(method.getName())) {
            return providers().getOrDefault(method.getName(), () -> null).get();
        } else {
            try {
                final Parameter parameter = ssm().getParameter(new GetParameterRequest()
                        .withName(pathPrefix() + "/" + method.getName())
                        .withWithDecryption(decryption()))
                        .getParameter();
                LOGGER.debug("Found parameter {} for method {}", parameter.getName(), method);
                return parameter.getValue();
            } catch (ParameterNotFoundException nfe) {
                LOGGER.warn("Parameter not found for method {}", method);
                return null;
            }
        }
    }
}
