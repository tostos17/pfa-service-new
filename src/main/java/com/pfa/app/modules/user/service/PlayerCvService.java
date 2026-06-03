package com.pfa.app.modules.user.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.pfa.app.modules.user.entities.PlayerProfile;
import com.pfa.app.modules.user.repositories.PlayerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class PlayerCvService {

    private final PlayerProfileRepository playerRepository;

    public byte[] generatePlayerCvPdf(Long playerProfileId) {
        PlayerProfile player = playerRepository.findById(playerProfileId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Font.BOLD);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.ITALIC);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            // Header Section
            Paragraph nameHeader = new Paragraph(player.getUser().getFirstName() + " " + player.getUser().getLastName(), titleFont);
            nameHeader.setAlignment(Element.ALIGN_CENTER);
            document.add(nameHeader);

            Paragraph positionSub = new Paragraph("Position: " + player.getPosition() + " | Jersey #" + player.getJerseyNumber(), subTitleFont);
            positionSub.setAlignment(Element.ALIGN_CENTER);
            positionSub.setSpacingAfter(20);
            document.add(positionSub);

            document.add(new Paragraph("-------------------------------------------------------------------------------------------------------"));

            // Player Vital Statistics
            document.add(new Paragraph("BIOMETRIC & TECHNICAL PROFILE", boldFont));
            document.add(new Paragraph("Date of Birth: " + player.getDateOfBirth().toString(), bodyFont));
            document.add(new Paragraph("Dominant Foot: " + player.getDominantFoot(), bodyFont));
            document.add(new Paragraph("Height: " + player.getHeightCm() + " cm", bodyFont));
            document.add(new Paragraph("Weight: " + player.getWeightKg() + " kg", bodyFont));

            // Placeholder for future aggregate statistics (Module 3 & 4)
            document.add(new Paragraph("\nACADEMY PERFORMANCE SUMMARY", boldFont));
            document.add(new Paragraph("Match stats, attendance data, and performance rankings will display here automatically.", subTitleFont));

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate CV PDF", e);
        }

        return out.toByteArray();
    }
}
