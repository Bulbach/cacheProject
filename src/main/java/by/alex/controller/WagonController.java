package by.alex.controller;

import by.alex.dto.WagonDto;
import by.alex.exceptions.CacheException;
import by.alex.service.WagonService;
import by.alex.util.print.PrintInfo;
import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class WagonController extends HttpServlet {
    private final WagonService wagonService;
    private final Gson gson;
    private final PrintInfo printInfo;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String pathInfo = request.getPathInfo();
        log.info("PathInfo" + pathInfo + " in servlet method doGet");
        String[] pathParts = pathInfo.split("/");
        String action = pathParts[1];

        switch (action) {
            case "wagon":
                getById(request, response);
                break;
            case "wagon-pdf":
                getByIdPdf(request, response);
                break;
            case "wagons":
                showAllUnits(request, response);
                break;
            default:
                break;
        }
    }

    private void getById(HttpServletRequest request, HttpServletResponse response) throws IOException {

        WagonDto wagon;
        UUID id;
        String pathInfo = request.getPathInfo();
        log.info("PathInfo" + pathInfo + " in servlet method getById");
        String parseID = parseID(pathInfo);
        if (isParameterUUID(parseID)) {
            id = getUUID(parseID);
        } else {
            String unitId = request.getParameter("id");
            id = UUID.fromString(unitId);
        }
        if (wagonService.isExist(id)) {
            wagon = wagonService.getById(id);
            String wagonsJson = gson.toJson(wagon);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            try {
                response.getWriter().write(wagonsJson);
            } catch (IOException e) {
                getNotFoundAnswer(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing 'id' parameter.");
                throw new RuntimeException(e);
            }
        } else {
            getNotFoundAnswer(response, HttpServletResponse.SC_BAD_REQUEST, "Неверный или отсутствующий параметр 'id'.");
        }
    }

    private void getByIdPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String pathInfo = request.getPathInfo();
        log.info("PathInfo" + pathInfo + " in servlet method getByIdPdf");
        String parseID = parseID(pathInfo);
        if (isParameterUUID(parseID)) {
            UUID id = getUUID(parseID);
            if (wagonService.isExist(id)) {
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
                    getNotFoundAnswer(response, HttpServletResponse.SC_BAD_REQUEST, "Ошибка при генерации PDF.");
                    throw new RuntimeException(e);
                }
            } else {
                getNotFoundAnswer(response, HttpServletResponse.SC_BAD_REQUEST, "Неверный или отсутствующий параметр 'id'.");
            }
        } else {
            getNotFoundAnswer(response, HttpServletResponse.SC_BAD_REQUEST, "Неверный или отсутствующий параметр 'id'.");
        }
    }

    private static void getNotFoundAnswer(HttpServletResponse response, int scBadRequest, String s) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(scBadRequest);
        response.getWriter().write(s);
    }

    private void showAllUnits(HttpServletRequest request, HttpServletResponse response) {

        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int pageSize = 20;
        if (request.getParameter("per_page") != null) {
            pageSize = Integer.parseInt(request.getParameter("per_page"));
        }

        List<WagonDto> wagons = Collections.unmodifiableList(wagonService.getAll(page, pageSize));

        String wagonsJson = gson.toJson(wagons);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(wagonsJson);
        } catch (IOException e) {
            log.error("Mistake with method showAllUnits" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String json = getRequestParametersToJson(request);
        if (json != null) {
            WagonDto wagonDto = gson.fromJson(json, WagonDto.class);
            if (wagonService.isExist(wagonDto.getWagonNumber())) {

                getNotFoundAnswer(response, HttpServletResponse.SC_OK, "Wagon with ID " + wagonDto.getWagonNumber() + " is already exist.");
            } else {
                WagonDto createdWagon = wagonService.create(wagonDto);

                getNotFoundAnswer(response, HttpServletResponse.SC_OK, "Wagon with ID " + createdWagon.getId() + " has been created successfully.");
            }
        } else {
            getNotFoundAnswer(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing parameters for creating wagon.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String json = getRequestParametersToJson(request);
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
            getNotFoundAnswer(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing parameters for updating wagon.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String parseID = parseID(pathInfo);
        if (isParameterUUID(parseID)) {
            UUID id = getUUID(parseID);
            wagonService.delete(id);

            getNotFoundAnswer(response, HttpServletResponse.SC_OK, "Wagon with ID " + id + " has been deleted successfully.");
        } else {
            getNotFoundAnswer(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing 'id' parameter.");
        }
    }

    private static String getRequestParametersToJson(HttpServletRequest request) throws IOException {

        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }

    private String parseID(String requestPath) {
        String[] parts = requestPath.split("/");
        int length = parts.length;
        return parts[length - 1].replaceAll("\"", "");
    }

    private UUID getUUID(String path) {
        UUID id = null;
        if (isParameterUUID(path)) {
            String parseID = parseID(path);
            id = UUID.fromString(parseID);
        }
        return id;
    }

    private static boolean isParameterUUID(String path) {
        return path.matches("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
    }
}

