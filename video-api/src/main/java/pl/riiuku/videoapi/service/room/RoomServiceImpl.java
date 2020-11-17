package pl.riiuku.videoapi.service.room;

import org.springframework.stereotype.Service;
import pl.riiuku.videoapi.api.RoomRequest;
import pl.riiuku.videoapi.domain.Room;
import pl.riiuku.videoapi.repository.RoomRepository;

import java.util.List;
import java.util.Set;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room createNewRoom(RoomRequest roomRequest) {
        StringBuilder name = new StringBuilder(roomRequest.name);
        List<String> savedNames = roomRepository.findAllNamesLike(name.toString());
        if (savedNames.size() != 0 && savedNames.contains(name.toString())) {
            int numberName = 0;
            do {
                numberName++;
            } while (savedNames.contains(name + "_" + numberName));
            name.append("_").append(numberName);
        }
        return roomRepository.save(new Room(name.toString(), roomRequest.maxSize));

    }


}