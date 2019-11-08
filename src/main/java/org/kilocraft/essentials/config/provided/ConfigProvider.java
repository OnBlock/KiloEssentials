package org.kilocraft.essentials.config.provided;

import org.kilocraft.essentials.api.config.ConfigValueGetter;
import org.kilocraft.essentials.config.KiloConifg;

public class ConfigProvider {
    private ConfigValueGetter main;
    private ConfigValueGetter messages;
    private ConfigValueGetter commands;

    public ConfigProvider() {
        main = new ConfigValueGetter(KiloConifg.getFileConfigOfMain());
        messages = new ConfigValueGetter(KiloConifg.getFileConfigOfMessages());
        commands = new ConfigValueGetter(KiloConifg.getFileConfigOfCommands());
    }

    public ConfigValueGetter getMain() {
        return main;
    }

    public ConfigValueGetter getMessages() {
        return messages;
    }

    public ConfigValueGetter getCommands() {
        return commands;
    }

}