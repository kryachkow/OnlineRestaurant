package com.onlinerestaurant.servlet.controller;

import com.onlinerestaurant.servlet.command.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

class ControllerTest {

    static MockedStatic<CommandContainer> containerMockedStatic;

    @BeforeAll
    static void setUp(){
        containerMockedStatic = mockStatic(CommandContainer.class);
    }

    @AfterAll
    static void tearDown(){
        containerMockedStatic.clearInvocations();
    }


    @Test
    void doGet() throws Exception {
        final Controller controller = new Controller();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final Command command = mock(Command.class);
        final HttpSession session = mock(HttpSession.class);

        when(request.getParameter("command")).thenReturn("testCommand");
        containerMockedStatic.when(() ->CommandContainer.getCommand("testCommand")).thenReturn(command);
        when(command.execute(request,response)).thenReturn("testJSP.jsp");
        when(request.getRequestDispatcher("testJSP.jsp")).thenReturn(dispatcher);

        controller.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("testJSP.jsp");
        verify(dispatcher).forward(request,response);

        when(request.getParameter("command")).thenReturn("testCommand");
        containerMockedStatic.when(() ->CommandContainer.getCommand("testCommand")).thenReturn(command);
        when(command.execute(request,response)).thenThrow(new Exception("exceptionCheck"));
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("error.jsp")).thenReturn(dispatcher);

        controller.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("error.jsp");
        verify(dispatcher, times(2)).forward(request,response);

    }

    @Test
    void doPost() throws Exception {
        final Controller controller = new Controller();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Command command = mock(Command.class);
        final HttpSession session = mock(HttpSession.class);

        when(request.getParameter("command")).thenReturn("testCommand");
        containerMockedStatic.when(() ->CommandContainer.getCommand("testCommand")).thenReturn(command);
        when(command.execute(request,response)).thenReturn("testJSP.jsp");

        controller.doPost(request, response);
        verify(response).sendRedirect("testJSP.jsp");

        when(request.getParameter("command")).thenReturn("testCommand");
        containerMockedStatic.when(() ->CommandContainer.getCommand("testCommand")).thenReturn(command);
        when(command.execute(request,response)).thenThrow(new Exception("exceptionCheck"));

        when(request.getSession()).thenReturn(session);

        controller.doPost(request, response);
        verify(response).sendRedirect("error.jsp");
        verify(request).getSession();


    }
}