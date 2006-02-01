package pt.utl.ist.elab.client.telescopio;

/*
 * Ceu.java vers�o 2.0
 *
 * Created on 23 de Outubro de 2004, 23:29
 */

/**
 *
 * @author  Myriam
 */
public class Ceu {
  
    public static ObjectoCeleste[] objectos_visiveis = new ObjectoCeleste[100];
    public static int comprimentoVisivel;
    
    /** Gerar objetos do ceu
     ObjectoCeleste(String _nomeObjecto String _nome ,String _comentario, int _ar_h, double _ar_m, int _dec_g ,double _dec_m, double _dt)*/
    public Ceu() {
        int i=0; 
              
        //Constela��o de Andromeda 
       ObjectoCeleste M32 = new ObjectoCeleste(":LM0032#","M32","Galaxia eliptica satelite da galaxia de Andomeda", 0, 42.8 , 40, 52.1, 3600.0  );
        
        if (M32.visivel == true) 
        {objectos_visiveis[i]=M32; 
        i++; }
       
        ObjectoCeleste M110 = new ObjectoCeleste(":LM0110#","M110 Andromeda","Galaxia espiral satelite da galaxia de Andomeda",0,40.4,41,41.1, 7200.0);

        if (M110.visivel == true) { 
        objectos_visiveis[i]=M110; 
        i++; }
        
        
        ObjectoCeleste M31 = new ObjectoCeleste(":LM0031#","M31 Andromeda","Grande galaxia espiral de Andomeda", 0, 42.8 , 41, 16.1, 3600.0  );
        
        if (M31.visivel == true) 
        {objectos_visiveis[i]=M31; 
        i++; }
                
        // Constela��o do Aquario 
        
        ObjectoCeleste M2 = new ObjectoCeleste(":LM0002#","M2 Aquarius","Enxame Globular constela��o do Aquario.", 21, 33.5 , -0, 49.3, 3600.0  );
        
        if (M2.visivel == true) 
        {objectos_visiveis[i]=M2; 
        i++; }
        
        ObjectoCeleste M72 = new ObjectoCeleste(":LM0072#","M72 Aquarius","Enxame Globular ", 20, 53.5 , -12, 32.3, 7200.0  );
        
        if (M72.visivel == true) 
        {objectos_visiveis[i]=M72; 
        i++; }
        
        ObjectoCeleste NGC7009 = new ObjectoCeleste(":LC7009#","NGC7009 Aquarius","Nebulosa Saturno planetaria", 21, 4.2 , -11, 22.3, 3600.0  );
        
        if (NGC7009.visivel == true) 
        {objectos_visiveis[i]=NGC7009; 
        i++; }
        
        ObjectoCeleste NGC7293 = new ObjectoCeleste(":LC7293#","NGC7293 Aquarius","Nebulosa Planetaria Helice ", 22, 29.6 , -20, 48.4, 3600.0  );
        
        if (NGC7293.visivel == true) 
        {objectos_visiveis[i]=NGC7293; 
        i++; }
        
               
        // Constela��o da Girafa (Camelopardalis )
         
        ObjectoCeleste NGC2403 = new ObjectoCeleste(":LC2403#","NGC2403 Camelopardis","Galaxia espiral", 7, 36.8 , 65, 37.0, 3600.0  );
        
        if (NGC2403.visivel == true) 
        {objectos_visiveis[i]=NGC2403; 
        i++; }
        
         // Constela��o do Cancer
                
        ObjectoCeleste M1 = new ObjectoCeleste(":LM0001#","M1 Cancer","Nebulosa do Carangueijo",5,34.5,22,1.1, 3600.0);

        if (M1.visivel == true) { 
        objectos_visiveis[i]=M1; 
        i++; }
         
         //Cassiopeia 
        
        ObjectoCeleste M45 = new ObjectoCeleste(":LM0045#","M45 Tauro","Pleiades",5,34.5,22,1.1, 3600.0);

        if (M45.visivel == true) { 
        objectos_visiveis[i]=M45; 
        i++; }
        // Constela�ao dos Caes de Ca�a (Canes Venaciti)
        
        ObjectoCeleste M106 = new ObjectoCeleste(":LM0106","M106 Canes Venaciti","Galaxia espiral", 12, 18.9 , 47, 19.1, 3600.0  );
        
        if (M106.visivel == true) 
        {objectos_visiveis[i]=M106; 
        i++; }
        
        ObjectoCeleste NGC4490 = new ObjectoCeleste(":LM0106#","NGC4490 Canes Venaciti","Galaxia espiral", 12, 30.6 , 41, 39.1, 3600.0  );
        
        if (NGC4490.visivel == true) 
        {objectos_visiveis[i]=NGC4490; 
        i++; }
         
        ObjectoCeleste M94 = new ObjectoCeleste(":LM0094#","M94 Canes Venaciti","Galaxia espiral", 12, 50.9 , 41, 8.1, 7200.0  );
        
        if (M94.visivel == true) 
        {objectos_visiveis[i]=M94; 
        i++; }
         
        ObjectoCeleste M63 = new ObjectoCeleste(":LM0063#","M63 Canes Venaciti","Galaxias espiral", 13, 11.0 , 37, 3.1, 7200.0  );
        
        if (M63.visivel == true) 
        {objectos_visiveis[i]=M63; 
        i++; }
        
        ObjectoCeleste M51 = new ObjectoCeleste(":LM0051#","M51 Canes Venaciti","Galaxias espiral tambem chamada Galaxia Redemoinho", 13, 30.1 , 47, 16.1, 7200.0  );
        
        if (M51.visivel == true) 
        {objectos_visiveis[i]=M51; 
        i++; }
        
        ObjectoCeleste M3 = new ObjectoCeleste(":LM0003#","M3 Canes Venaciti","Enxame Globular", 13, 42.2 , 28, 23.1, 7200.0  );
        
        if (M3.visivel == true) 
        {objectos_visiveis[i]=M3; 
        i++; }
        
        // Constela��o do Capricornio (Capricornus)
        ObjectoCeleste M30 = new ObjectoCeleste(":LM0030#","M30 Capricornus","Enxame globular", 21, 40.4 , -23, 11.4, 15.0  );
        
        if (M30.visivel == true) 
        {objectos_visiveis[i]=M30; 
        i++; }
        
      /*  ObjectoCeleste Algiedi = new ObjectoCeleste("Algiedi Capricornus","Sistema de varias estrelas duplas", 13, 11.0 , 37, 3.1, 15.0  );
        
        if (M63.visivel == true) 
        {objectos_visiveis[i]=M63; 
        i++; }*/
        
               
        // Constela��o do Centauro
        ObjectoCeleste NGC5128 = new ObjectoCeleste(":LM5128#","NGC5128 Centauro","Galaxia do Centauro", 13, 25.5 , -43, 1.2, 7200.0  );
        
        if (NGC5128.visivel == true) 
        {objectos_visiveis[i]=NGC5128; 
        i++; }
        
        ObjectoCeleste NGC5139 = new ObjectoCeleste(":LM5139#","NGC5139 Centauro","Enxame Globular", 13, 26.8 , -47, 28.8, 7200.0  );
        
        if (NGC5128.visivel == true) 
        {objectos_visiveis[i]=NGC5128; 
        i++; }
        
        ObjectoCeleste acentauro = new ObjectoCeleste(":LS0150#","Alfa Centauro","Estrela mais proxima do Sol", 14, 39.6 , -60, 49.8, 7200.0  );
        
        if (acentauro.visivel == true) 
        {objectos_visiveis[i]=acentauro; 
        i++; }
        
        // Constela��o da Baleia (Cetus)
        ObjectoCeleste M77 = new ObjectoCeleste(":LM0077#","M77 Cetus","Galaxia espiral", 2, 42.7 , -00, 2.2, 7200.0  );
        
        if (M77.visivel == true) 
        {objectos_visiveis[i]=M77; 
        i++; }
        
        // Constela��o da Cabeleira de Berenice (Coma Berenice)
        ObjectoCeleste M98 = new ObjectoCeleste(":LM0098#","M98 Coma Berenice","Galaxia espiral", 12, 13.8 , 14, 54.0, 7200.0  );
        
        if (M98.visivel == true) 
        {objectos_visiveis[i]=M98; 
        i++; }
        
        ObjectoCeleste M99 = new ObjectoCeleste(":LM 0099#","M99 Coma Berenice","Galaxia espiral", 12, 18.9 , 14, 26.2, 7200.0  );
        
        if (M99.visivel == true) 
        {objectos_visiveis[i]=M99; 
        i++; }
        
        ObjectoCeleste M100 = new ObjectoCeleste(":LM 0100#","M100 Coma Berenice","Galaxia espiral", 12, 23.0 , 15, 50.2, 15.0  );
        
        if (M100.visivel == true) 
        {objectos_visiveis[i]=M100; 
        i++; }
        
        ObjectoCeleste M85 = new ObjectoCeleste(":LM0085#","M85 Coma Berenice","Galaxia espiral", 12, 25.5 , 18, 12.2, 7200.0  );
        
        if (M85.visivel == true) 
        {objectos_visiveis[i]=M85; 
        i++; }
        
        ObjectoCeleste M88 = new ObjectoCeleste(":LM0088#","M88 Coma Berenice","Galaxia espiral", 12, 31.1 , 14, 26.2, 7200.0  );
        
        if (M88.visivel == true) 
        {objectos_visiveis[i]=M88; 
        i++; }
        
         ObjectoCeleste M91 = new ObjectoCeleste(":LM 0064#","M64 Coma Berenice","Galaxia espiral", 12, 56.7 , 21, 41.1, 7200.0  );
        
        if (M91.visivel == true) 
        {objectos_visiveis[i]=M91; 
        i++; }
         
        ObjectoCeleste M53 = new ObjectoCeleste(":LM 0053#","M53 Coma Berenice","Enxame globular", 13, 12.9 , 18, 10.2, 7200.0  );
        
        if (M53.visivel == true) 
        {objectos_visiveis[i]=M53; 
        i++; }
        
        //Constela��o do Cisne (Cygnus)
        ObjectoCeleste albireo = new ObjectoCeleste(":LS0223#","Albireo Cygnus","Estrela dupla", 19, 30.7 , 27, 57.6, 7200.0  );
        
        if (albireo.visivel == true) 
        {objectos_visiveis[i]=albireo; 
        i++; }
        
        ObjectoCeleste NGC7000 = new ObjectoCeleste(":LC7000#","NGC7000 Cygnus","Nebulosa America do Norte", 20, 58.8 , 44, 20.2, 7200.0  );
        
        if (NGC7000.visivel == true) 
        {objectos_visiveis[i]=NGC7000; 
        i++; }
        
        
        //Constela��o de Hercules 
        ObjectoCeleste M13 = new ObjectoCeleste(":LM0013#","M13 Hercules","Enxame Globular visivel a olho nu", 16, 41.7, 36, 28.1, 3600.0  );
        
        if (M13.visivel == true) 
        {objectos_visiveis[i]=M13; 
        i++; }
        
        ObjectoCeleste M92 = new ObjectoCeleste(":LM0092#","M92 Hercules","Enxame globular", 17, 17.1 , 43, 8.1, 7200.0  );
        
        if (M92.visivel == true) 
        {objectos_visiveis[i]=M92; 
        i++; }
        
        // Constela��o da hidra (Hydra)
        ObjectoCeleste M68 = new ObjectoCeleste(":LM0068#","M68 Hydra","Enxame globular", 12, 39.5 , -26, 45.4, 7200.0  );
        
        if (M68.visivel == true) 
        {objectos_visiveis[i]=M68; 
        i++; }
        
       ObjectoCeleste M83 = new ObjectoCeleste(":LM0083#","M83 Hydra","Galaxia Espiral", 13, 37.1 , -29, 52.4, 7200.0  );
        
        if (M83.visivel == true) 
        {objectos_visiveis[i]=M83; 
        i++; }
       
       //Constela�ao do Le�o (Leo)
       ObjectoCeleste NGC2903 = new ObjectoCeleste(":LC2903#","NGC2903 Leo","Galaxia espiral", 9, 32.2 , 21, 29.1, 7200.0  );
        
        if (NGC2903.visivel == true) 
        {objectos_visiveis[i]=NGC2903; 
        i++; }
       
       ObjectoCeleste M95 = new ObjectoCeleste(":LM0095#","M95 Leo","Galaxia Espiral em Barra", 10, 44.0 , 11, 42.2, 7200.0  );
        
        if (M95.visivel == true) 
        {objectos_visiveis[i]=M95; 
        i++; }
       
        ObjectoCeleste M96 = new ObjectoCeleste(":LM0096#","M96 Leo","Galaxia Espiral ", 10, 46.8 , 11, 49.2, 7200.0  );
        
        if (M96.visivel == true) 
        {objectos_visiveis[i]=M96; 
        i++; }
       
        ObjectoCeleste M105 = new ObjectoCeleste(":LM0105#","M105 Leo","Galaxia Eliptica", 10, 47.8 , 12, 35.2, 15.0  );
        
        if (M105.visivel == true) 
        {objectos_visiveis[i]=M105; 
        i++; }
         
        ObjectoCeleste M65 = new ObjectoCeleste(":LM0065#","M65 Leo","Galaxia Espiral ", 11, 18.9 , -0, 2.2, 7200.0  );
        
        if (M65.visivel == true) 
        {objectos_visiveis[i]=M65; 
        i++; }
        
        ObjectoCeleste M66 = new ObjectoCeleste(":LM0066#","M66 Leo","Galaxia Espiral ", 11, 20.2 , 12, 59.2, 7200.0  );
        
        if (M66.visivel == true) 
        {objectos_visiveis[i]=M66; 
        i++; }
       
        // Constela��o da Lira (Lyra)
          ObjectoCeleste M57 = new ObjectoCeleste(":LM0057#","M57 Lyra","Nebulosa Planetaria ", 18, 53.6 , 33, 2.1, 15.0  );
        
        if (M57.visivel == true) 
        {objectos_visiveis[i]=M57; 
        i++; }
          
        ObjectoCeleste M56 = new ObjectoCeleste(":LM0053#","M56 Lyra","Enxame globular", 19, 16.6 , 30, 11.1, 7200.0  );
        
        if (M56.visivel == true) 
        {objectos_visiveis[i]=M56; 
        i++; }
       
        //Constela��o do Unicornio (Monoceros)
        ObjectoCeleste NGC2261 = new ObjectoCeleste(":LC2261#","NGC2261 Monoceros","Nebulosa variavel de Hubble", 6, 39.2 , 8, 44.2, 7200.0  );
        
        if (NGC2261.visivel == true) 
        {objectos_visiveis[i]=NGC2261; 
        i++; }
        
       
        
         //Constela��o do Ofi�co
         
         ObjectoCeleste M107 = new ObjectoCeleste(":LM0107#","M107 Ophiuchus","Enxame Globular", 16, 32.5 , -13, 3.2, 3600.0  );
        
        if (M107.visivel == true) 
        {objectos_visiveis[i]=M107; 
        i++; }
        
        ObjectoCeleste M12 = new ObjectoCeleste(":LM0012#","M12 Ophiuchus","Enxame Globular", 16, 47.2 , -1, 57.2, 3600.0  );
        
        if (M12.visivel == true) 
        {objectos_visiveis[i]=M12; 
        i++; }
        
       ObjectoCeleste M10 = new ObjectoCeleste(":LM0010#","M10 Ophiuchus","Enxame Globular", 16, 57.1 , -4, 6.2, 3600.0  );
        
        if (M10.visivel == true) 
        {objectos_visiveis[i]=M10; 
        i++; }
       
       ObjectoCeleste M62 = new ObjectoCeleste(":LM0062#","M62 Ophiuchus","Enxame Globular", 17, 1.2 , -30, 7.4, 10.0  );
        
        if (M62.visivel == true) 
        {objectos_visiveis[i]=M62; 
        i++; }
       
       ObjectoCeleste M14 = new ObjectoCeleste(":LM0014#","M14 Ophiuchus","Enxame Globular", 17, 37.6 , -3, 15.2, 3600.0  );
        
        if (M14.visivel == true) 
        {objectos_visiveis[i]=M14; 
        i++; }
       
       //Orion
       ObjectoCeleste M42 = new ObjectoCeleste(":LM0042#","M42 Ophiuchus","Enxame Globular", 17, 37.6 , -3, 15.2, 3600.0  );
        
        if (M14.visivel == true) 
        {objectos_visiveis[i]=M42; 
        i++; }
       
 //Estrelas 
        
       ObjectoCeleste ALBIREO = new ObjectoCeleste(":LS0223#","Albireo CYGNUS ","Estrela_Bico do cisne",19,30.8,27,58.0, 1);

        if (ALBIREO.visivel == true) { 
        objectos_visiveis[i]=ALBIREO; 
        i++; }
       
        ObjectoCeleste ALDEBARAN= new ObjectoCeleste(":LS0033#","Aldebaran Toro ","Estrela. Olho do toro",4,35.9,16,31.0, 1);

        if (ALDEBARAN.visivel == true) { 
        objectos_visiveis[i]=ALDEBARAN; 
        i++; }
        
        ObjectoCeleste ALTAIR= new ObjectoCeleste(":LS0226#","Altair AGUIA ","Estrela. Olho da aguia",19,50.8,8,52.0, 1);

        if (ALTAIR.visivel == true) { 
        objectos_visiveis[i]=ALTAIR; 
        i++; }
       
        ObjectoCeleste ANTARES= new ObjectoCeleste(":LS0177#","Antares SCORPIAO ","Estrela. Olho da escorpião",14,15.7,19,11.0, 1);

        if (ANTARES.visivel == true) { 
        objectos_visiveis[i]=ANTARES; 
        i++; }
        
        ObjectoCeleste ARCTURUS= new ObjectoCeleste(":LS0147#","Arcturus BOOTS ","Estrela",16,29.5,-26,26.0, 1);

        if (ARCTURUS.visivel == true) { 
        objectos_visiveis[i]=ARCTURUS; 
        i++; }
         
        ObjectoCeleste BETELGUESE= new ObjectoCeleste(":LS0056#","Betelguese ORION ","Estrela",5,55.2,7,25.0, 1);

        if (BETELGUESE.visivel == true) { 
        objectos_visiveis[i]=BETELGUESE; 
        i++; }
         
        ObjectoCeleste CAPELLA= new ObjectoCeleste(":LS0042#","Capella AURIGA ","Estrela",5,16.6,46,0.0, 1);

        if (CAPELLA.visivel == true) { 
        objectos_visiveis[i]=CAPELLA; 
        i++; }
        
        ObjectoCeleste DENEB= new ObjectoCeleste(":LS0232#","Deneb CYGNUS ","Estrela",20,41.5,45,17.0, 1);

        if (DENEB.visivel == true) { 
        objectos_visiveis[i]=DENEB; 
        i++; }
         
        ObjectoCeleste POLARIS= new ObjectoCeleste(":LS0019#","Polaris URSA MINOR ","Estrela",2,14.7,89,17.0, 1);

        if (POLARIS.visivel == true) { 
        objectos_visiveis[i]=POLARIS; 
        i++; }
         
        ObjectoCeleste POLLUX= new ObjectoCeleste(":LS0081#","Pollux GEMINI ","Estrela",7,45.4,28,2.0, 1);

        if (POLLUX.visivel == true) { 
        objectos_visiveis[i]=POLLUX; 
        i++; }
         
        ObjectoCeleste SIRIUS= new ObjectoCeleste(":LS0067#","Sirius CANIS MAJOR ","Estrela",6,45.2,-16,43.0, 1);

        if (SIRIUS.visivel == true) { 
        objectos_visiveis[i]=SIRIUS; 
        i++; }
         
        ObjectoCeleste RIGEL= new ObjectoCeleste(":LS0041#","Rigel ORION ","Estrela",5,14.6,-8,12.0, 1);

        if (RIGEL.visivel == true) { 
        objectos_visiveis[i]=RIGEL; 
        i++; }
        
        ObjectoCeleste SPICA= new ObjectoCeleste(":LS0138#","Spica VIRGO ","Estrela",13,25.2,-11,10.0, 1);

        if (SPICA.visivel == true) { 
        objectos_visiveis[i]=SPICA; 
        i++; }
        
        ObjectoCeleste VEGA= new ObjectoCeleste(":LS0214#","Vega LYRA ","Estrela",18,37.0,38,47.0, 1);

        if (VEGA.visivel == true) { 
        objectos_visiveis[i]=VEGA; 
        i++; }

        
       
       comprimentoVisivel=i;
    }
    
}

