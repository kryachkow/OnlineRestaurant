package com.onlinerestaurant.servlet;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

/**
 * Shows errorMessage and removes errorMessage if it exists.
 */
public class MessageHandlerTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        if (pageContext.getSession().getAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE) != null) {
            JspWriter out = pageContext.getOut();
            try {
                out.print(pageContext.getSession().getAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE));
            } catch (IOException e) {
                throw new JspException();
            }
            pageContext.getSession().removeAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE);
        }
        return SKIP_BODY;
    }
}
