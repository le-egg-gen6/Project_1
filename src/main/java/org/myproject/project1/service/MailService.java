package org.myproject.project1.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.myproject.project1.config.mail.MailSenderConfig;
import org.myproject.project1.config.security.user.UserDetailsImpl;
import org.myproject.project1.db.AccountService;
import org.myproject.project1.db.DBAccount;
import org.myproject.project1.exception.custom.ResourceNotFoundException;
import org.myproject.project1.utils.RandomUtils;
import org.myproject.project1.utils.SecurityContextUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author nguyenle
 * @since 11:07 PM Thu 12/5/2024
 */
@Service
@RequiredArgsConstructor
public class MailService {

    private final ThreadPoolService threadPoolService;

    private final AccountService accountService;

    private final MailSenderConfig config;

    private final JavaMailSender mailSender;

    private final String VERIFICATION_MAIL_SUBJECT = "Verification email";

    public boolean verifyAccount(String verificationToken) {
        UserDetailsImpl userDetails = SecurityContextUtils.getCurrentUserDetails();
        String userId = userDetails.getId();
        DBAccount account = accountService.getAccount(userId);
        if (account == null) {
            throw new ResourceNotFoundException("Account not found");
        }
        boolean isTokenValid = verificationToken.equals(account.getVerificationCode());
        account.setValidated(isTokenValid);
        accountService.saveAsync(account);
        return isTokenValid;
    }

    private void sendMail(String toAddress, String content, String subject) {
        String fromAddress = config.getUsername();
        String senderName = config.getSender();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
            threadPoolService.submitAsynchronousTask(() -> mailSender.send(mimeMessage));
        } catch (Exception ignore) {
        }
    }

    public void sendVerificationToken(DBAccount account) {
        String toAddress = account.getEmail();
        String verificationCode = RandomUtils.randomValidationToken();
        account.setVerificationCode(verificationCode);
        accountService.saveAsync(account);
        String content = "Code: " + verificationCode;
        sendMail(toAddress, content, VERIFICATION_MAIL_SUBJECT);
    }

    public void resendMail() {
        UserDetailsImpl userDetails = SecurityContextUtils.getCurrentUserDetails();
        String userId = userDetails.getId();
        DBAccount account = accountService.getAccount(userId);
        if (account == null) {
            throw new ResourceNotFoundException("Account not found");
        }
        sendVerificationToken(account);
    }

}
