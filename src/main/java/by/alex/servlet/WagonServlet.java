package by.alex.servlet;

import by.alex.Runner;
import by.alex.dto.WagonDto;
import by.alex.exceptions.CacheException;
import by.alex.service.WagonService;
import by.alex.util.print.PrintInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.kernel.pdf.PdfDocument;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@WebServlet("/wagons/*")
public class WagonServlet extends HttpServlet {

    private WagonService wagonService;
    private Gson gson;
    private PrintInfo printInfo;

    @Override
    public void init() throws ServletException {
        Runner runner = new Runner();
        printInfo = new PrintInfo();
        wagonService = runner.getObject(WagonService.class);
        gson = new Gson();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String action = pathParts[1];

        switch (action) {
            case "create":
                createUser(request, response);
                break;
            case "delete":
                deleteUnit(request, response);
                break;
            case "update":
                updateUnit(request, response);
                break;
            default:
                doGet(request, response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String action = pathParts[1];

        System.out.println(action);
        switch (action) {
            case "wagons":
                showAllUnits(request, response);
                break;
            case "wagon":
                getById(request, response);
                break;
            case "wagon-pdf":
                getByIdPdf(request, response);
                break;
            default:
                break;
        }
    }

    private void getById(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String unitId = request.getParameter("id");


        UUID id = UUID.fromString(unitId);
        WagonDto wagon = wagonService.getById(id);

        String wagonsJson = gson.toJson(wagon);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(wagonsJson);
        } catch (IOException e) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid or missing 'id' parameter.");
            throw new RuntimeException(e);
        }
    }

    private void getByIdPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String unitId = request.getParameter("id");

        if (unitId != null) {
            UUID id = UUID.fromString(unitId);
            WagonDto wagon = wagonService.getById(id);
            try (OutputStream out = response.getOutputStream()) {
                PdfDocument printObject = printInfo.getPdfObject(wagon, out);
                byte[] xmpMetadata = printObject.getXmpMetadata();
                response.setContentType("application/pdf");
                response.setCharacterEncoding("UTF-8");
                response.addHeader("Content-Disposition", "inline; filename=\"wagon.pdf\"");
                response.setContentLength(xmpMetadata.length);
                response.getOutputStream().write(xmpMetadata);
                response.flushBuffer();

            } catch (CacheException | IOException e) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Ошибка при генерации PDF.");
                throw new RuntimeException(e);
            }
        } else {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Неверный или отсутствующий параметр 'id'.");
        }
    }

    private void updateUnit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = getRequestParametrsToJson(request);
        if (json != null) {
            WagonDto wagonDto = gson.fromJson(json, WagonDto.class);
            WagonDto updatedWagon = wagonService.update(wagonDto);

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            if (updatedWagon != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Wagon with ID " + updatedWagon.getId() + " has been updated successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Wagon not found or failed to update.");
            }
        } else {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid or missing parameters for updating wagon.");
        }
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String json = getRequestParametrsToJson(request);
        if (json != null) {
            WagonDto wagonDto = gson.fromJson(json, WagonDto.class);
            WagonDto createdWagon = wagonService.create(wagonDto);

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Wagon with ID " + createdWagon.getId() + " has been created successfully.");

        } else {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid or missing parameters for creating wagon.");
        }
    }

    private void deleteUnit(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String idParameter = request.getParameter("id");
        if (idParameter != null) {
            UUID id = UUID.fromString(idParameter);
            wagonService.delete(id);

        } else {
            String json = getRequestParametrsToJson(request);
            if (json != null) {
                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                if (jsonObject.has("id")) {
                    String idFromJson = jsonObject.get("id").getAsString();
                    UUID id = UUID.fromString(idFromJson);
                    wagonService.delete(id);

                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Wagon with ID " + id + " has been deleted successfully.");
                } else {
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Invalid or missing 'id' parameter.");
                }
            }
        }
    }

    private static String getRequestParametrsToJson(HttpServletRequest request) throws IOException {

        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }

    private void showAllUnits(HttpServletRequest request, HttpServletResponse response) {

        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int pageSize = 20;
        if (request.getParameter("page-size") != null) {
            pageSize = Integer.parseInt(request.getParameter("page-size"));
        }

        List<WagonDto> wagons = Collections.unmodifiableList(wagonService.getAll(page, pageSize));

        String wagonsJson = gson.toJson(wagons);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(wagonsJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
