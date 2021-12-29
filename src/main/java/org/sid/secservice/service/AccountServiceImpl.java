package org.sid.secservice.service;

import javax.transaction.Transactional;

import org.sid.secservice.dao.AppRoleRepository;
import org.sid.secservice.dao.AppUserRepository;
import org.sid.secservice.entities.AppRole;
import org.sid.secservice.entities.AppUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class AccountServiceImpl implements AccountService{
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) {
        AppUser user = appUserRepository.findByUsername(username);
        if(user!= null) throw new RuntimeException("User already exists"); 
        if(!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");

        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUser.setActivated(true);
        appUserRepository.save(appUser);
        System.out.println(appUser);
        addRoleToUser(username, "USER");

        return appUser;
    }

    @Override
    public AppRole saveRole(AppRole role) {
        // TODO Auto-generated method stub
        return appRoleRepository.save(role) ;
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        // TODO Auto-generated method stub
        return appUserRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);

        appUser.getRoles().add(appRole);
        appUserRepository.flush();

        
    }
    
}
