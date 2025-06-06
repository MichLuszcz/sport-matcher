package paint.projekt.sport_matcher.adRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/ad-requests")
public class AdRequestController {

  private final AdRequestService adRequestService;

  @GetMapping
  public @ResponseBody List<AdRequestDTO> getAllAdRequests() {
    return adRequestService.getAllAdRequests();
  }
}