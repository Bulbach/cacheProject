package by.alex.util.print.adaptor;

import by.alex.dto.WagonDto;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class WagonDtoAdapter extends TypeAdapter<WagonDto> {
    @Override
    public void write(JsonWriter out, WagonDto wagonDto) throws IOException {
        out.beginObject();
        out.name("id").value(String.valueOf(wagonDto.id()));
        out.name("wagonNumber").value(wagonDto.wagonNumber());
        out.name("loadCapacity").value(wagonDto.loadCapacity());
        out.name("yearOfConstruction").value(wagonDto.yearOfConstruction());
        out.name("dateOfLastService").value(wagonDto.dateOfLastService().toString());
        out.endObject();
    }

    @Override
    public WagonDto read(JsonReader in) throws IOException {
        UUID id = null;
        String wagonNumber = null;
        int loadCapacity = 0;
        int yearOfConstruction = 0;
        LocalDate dateOfLastService = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "id":
                    id = UUID.fromString(in.nextString());
                    break;
                case "wagonNumber":
                    wagonNumber = in.nextString();
                    break;
                case "loadCapacity":
                    loadCapacity = in.nextInt();
                    break;
                case "yearOfConstruction":
                    yearOfConstruction = in.nextInt();
                    break;
                case "dateOfLastService":
                    dateOfLastService = LocalDate.parse(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new WagonDto(id, wagonNumber, loadCapacity, yearOfConstruction, dateOfLastService);
    }
}
