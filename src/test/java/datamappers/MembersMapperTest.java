package datamappers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import security.HttpClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MembersMapperTest {

    HttpClient httpClient = new HttpClient();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private DiningMapper diningMapper = new DiningMapper();



}