package com.Prash.Controller;

import com.Prash.model.User;
import com.Prash.model.Wallet;
import com.Prash.service.UserService;
import com.Prash.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService  walletService;
    @Autowired
    private UserService userService;
    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> WalletToWalletTransfer(@RequestHeader("Authorization") String jwt
    , @PathVariable Long walletId
            , @RequestBody) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
}
